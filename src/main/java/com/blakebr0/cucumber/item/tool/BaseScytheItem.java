package com.blakebr0.cucumber.item.tool;

import com.blakebr0.cucumber.iface.IEnableable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropsBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.SwordItem;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Method;
import java.util.List;
import java.util.function.Function;

public class BaseScytheItem extends SwordItem {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Method GET_SEED;

    private final float attackDamage;
    private final float attackSpeed;
    private final int range;

    static {
        GET_SEED = ObfuscationReflectionHelper.findMethod(CropsBlock.class, "func_199772_f");
    }

    public BaseScytheItem(IItemTier tier, int attackDamage, float attackSpeed,  int range, Function<Properties, Properties> properties) {
        super(tier, attackDamage, attackSpeed, properties.apply(new Properties()));
        this.attackDamage = attackDamage;
        this.attackSpeed = attackSpeed;
        this.range = range;
    }

    @Override
    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
        if (this instanceof IEnableable) {
            IEnableable enableable = (IEnableable) this;
            if (enableable.isEnabled())
                super.fillItemGroup(group, items);
        } else {
            super.fillItemGroup(group, items);
        }
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        PlayerEntity player = context.getPlayer();
        if (player == null)
            return ActionResultType.FAIL;

        BlockPos pos = context.getPos();
        Hand hand = context.getHand();
        Direction face = context.getFace();
        ItemStack stack = player.getHeldItem(hand);

        if (!player.canPlayerEdit(pos.offset(face), face, stack))
            return ActionResultType.FAIL;

        World world = context.getWorld();
        if (world.isRemote())
            return ActionResultType.SUCCESS;

        BlockPos.getAllInBox(pos.add(-range, 0, -range), pos.add(range, 0, range)).forEach(aoePos -> {
            if (stack.isEmpty())
                return;

            BlockState state = world.getBlockState(aoePos);
            Block block = state.getBlock();

            if (block instanceof CropsBlock) {
                CropsBlock crop = (CropsBlock) block;
                Item seed = getSeed(crop);
                if (crop.isMaxAge(state) && seed != null) {
                    List<ItemStack> drops = Block.getDrops(state, (ServerWorld) world, aoePos, world.getTileEntity(pos));
                    for (ItemStack drop : drops) {
                        Item item = drop.getItem();
                        if (!drop.isEmpty() && item == seed) {
                            drop.shrink(1);
                            break;
                        }
                    }

                    for (ItemStack drop : drops) {
                        if (!drop.isEmpty()) {
                            Block.spawnAsEntity(world, aoePos, drop);
                        }
                    }

                    stack.damageItem(1, player, entity -> {
                        entity.sendBreakAnimation(player.getActiveHand());
                    });

                    world.setBlockState(aoePos, crop.withAge(0));
                }
            }
        });

        return ActionResultType.SUCCESS;
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, PlayerEntity player, Entity entity) {
        if (player.getCooledAttackStrength(0.5F) >= 0.95F) {
            World world = player.getEntityWorld();
            double grow = (this.range >= 2 ? 1.0D + (this.range - 1) * 0.25D : 1.0D);
            List<LivingEntity> entities = world.getEntitiesWithinAABB(LivingEntity.class, entity.getBoundingBox().grow(grow, 0.25D, grow));

            for (LivingEntity aoeEntity : entities) {
                if (aoeEntity != player && aoeEntity != entity && !player.isOnSameTeam(entity)) {
                    aoeEntity.applyKnockback(0.4F, MathHelper.sin(player.rotationYaw * 0.017453292F), -MathHelper.cos(player.rotationYaw * 0.017453292F));
                    aoeEntity.attackEntityFrom(DamageSource.causePlayerDamage(player), this.attackDamage);
                }
            }

            world.playSound(null, player.getPosX(), player.getPosY(), player.getPosZ(), SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, player.getSoundCategory(), 1.0F, 1.0F);

            player.spawnSweepParticles();
        }

        return super.onLeftClickEntity(stack, player, entity);
    }

    public float getAttackDamage() {
        return this.attackDamage + this.getTier().getAttackDamage();
    }

    public float getAttackSpeed() {
        return this.attackSpeed;
    }

    private static Item getSeed(Block block) {
        try {
            return (Item) GET_SEED.invoke(block);
        } catch (Exception e) {
            LOGGER.error("Unable to get seed from crop {}", e.getLocalizedMessage());
        }

        return null;
    }
}

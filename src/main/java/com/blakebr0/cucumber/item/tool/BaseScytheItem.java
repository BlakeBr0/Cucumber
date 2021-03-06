package com.blakebr0.cucumber.item.tool;

import com.blakebr0.cucumber.event.ScytheHarvestCropEvent;
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
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.MinecraftForge;
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
    public void fillItemCategory(ItemGroup group, NonNullList<ItemStack> items) {
        if (this instanceof IEnableable) {
            IEnableable enableable = (IEnableable) this;
            if (enableable.isEnabled())
                super.fillItemCategory(group, items);
        } else {
            super.fillItemCategory(group, items);
        }
    }

    @Override
    public ActionResultType useOn(ItemUseContext context) {
        PlayerEntity player = context.getPlayer();
        if (player == null)
            return ActionResultType.FAIL;

        BlockPos pos = context.getClickedPos();
        Hand hand = context.getHand();
        Direction face = context.getClickedFace();
        ItemStack stack = player.getItemInHand(hand);

        if (!player.mayUseItemAt(pos.relative(face), face, stack))
            return ActionResultType.FAIL;

        World world = context.getLevel();
        if (world.isClientSide())
            return ActionResultType.SUCCESS;

        int range = this.range;
        BlockPos.betweenClosed(pos.offset(-range, 0, -range), pos.offset(range, 0, range)).forEach(aoePos -> {
            if (stack.isEmpty())
                return;

            BlockState state = world.getBlockState(aoePos);

            ScytheHarvestCropEvent event = new ScytheHarvestCropEvent(world, aoePos, state, stack, player);
            if (MinecraftForge.EVENT_BUS.post(event))
                return;

            Block block = state.getBlock();

            if (block instanceof CropsBlock) {
                CropsBlock crop = (CropsBlock) block;
                Item seed = getSeed(crop);
                if (crop.isMaxAge(state) && seed != null) {
                    List<ItemStack> drops = Block.getDrops(state, (ServerWorld) world, aoePos, world.getBlockEntity(aoePos));
                    for (ItemStack drop : drops) {
                        Item item = drop.getItem();
                        if (!drop.isEmpty() && item == seed) {
                            drop.shrink(1);
                            break;
                        }
                    }

                    for (ItemStack drop : drops) {
                        if (!drop.isEmpty()) {
                            Block.popResource(world, aoePos, drop);
                        }
                    }

                    stack.hurtAndBreak(1, player, entity -> {
                        entity.broadcastBreakEvent(player.getUsedItemHand());
                    });

                    world.setBlockAndUpdate(aoePos, crop.getStateForAge(0));
                }
            }
        });

        return ActionResultType.SUCCESS;
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, PlayerEntity player, Entity entity) {
        if (player.getAttackStrengthScale(0.5F) >= 0.95F) {
            World world = player.level;
            double range = (this.range >= 2 ? 1.0D + (this.range - 1) * 0.25D : 1.0D);
            List<LivingEntity> entities = world.getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(range, 0.25D, range));

            for (LivingEntity aoeEntity : entities) {
                if (aoeEntity != player && aoeEntity != entity && !player.isAlliedTo(entity)) {
                    DamageSource source = DamageSource.playerAttack(player);
                    float attackDamage = this.getAttackDamage() * 0.67F;
                    boolean success = ForgeHooks.onLivingAttack(aoeEntity, source, attackDamage);

                    if (success) {
                        aoeEntity.knockback(0.4F, MathHelper.sin(player.yRot * 0.017453292F), -MathHelper.cos(player.yRot * 0.017453292F));
                        aoeEntity.hurt(source, attackDamage);
                    }
                }
            }

            world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.PLAYER_ATTACK_SWEEP, player.getSoundSource(), 1.0F, 1.0F);

            player.sweepAttack();
        }

        return super.onLeftClickEntity(stack, player, entity);
    }

    public float getAttackDamage() {
        return this.attackDamage + this.getTier().getAttackDamageBonus();
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

package com.blakebr0.cucumber.item.tool;

import com.blakebr0.cucumber.event.ScytheHarvestCropEvent;
import com.blakebr0.cucumber.iface.IEnableable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
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
        GET_SEED = ObfuscationReflectionHelper.findMethod(CropBlock.class, "func_199772_f");
    }

    public BaseScytheItem(Tier tier, int attackDamage, float attackSpeed,  int range, Function<Properties, Properties> properties) {
        super(tier, attackDamage, attackSpeed, properties.apply(new Properties()));
        this.attackDamage = attackDamage;
        this.attackSpeed = attackSpeed;
        this.range = range;
    }

    @Override
    public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) {
        if (this instanceof IEnableable) {
            IEnableable enableable = (IEnableable) this;
            if (enableable.isEnabled())
                super.fillItemCategory(group, items);
        } else {
            super.fillItemCategory(group, items);
        }
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();
        if (player == null)
            return InteractionResult.FAIL;

        BlockPos pos = context.getClickedPos();
        InteractionHand hand = context.getHand();
        Direction face = context.getClickedFace();
        ItemStack stack = player.getItemInHand(hand);

        if (!player.mayUseItemAt(pos.relative(face), face, stack))
            return InteractionResult.FAIL;

        Level world = context.getLevel();
        if (world.isClientSide())
            return InteractionResult.SUCCESS;

        int range = this.range;
        BlockPos.betweenClosed(pos.offset(-range, 0, -range), pos.offset(range, 0, range)).forEach(aoePos -> {
            if (stack.isEmpty())
                return;

            BlockState state = world.getBlockState(aoePos);

            ScytheHarvestCropEvent event = new ScytheHarvestCropEvent(world, aoePos, state, stack, player);
            if (MinecraftForge.EVENT_BUS.post(event))
                return;

            Block block = state.getBlock();

            if (block instanceof CropBlock) {
                CropBlock crop = (CropBlock) block;
                Item seed = getSeed(crop);
                if (crop.isMaxAge(state) && seed != null) {
                    List<ItemStack> drops = Block.getDrops(state, (ServerLevel) world, aoePos, world.getBlockEntity(aoePos));
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

        return InteractionResult.SUCCESS;
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
        if (player.getAttackStrengthScale(0.5F) >= 0.95F) {
            Level world = player.level;
            double range = (this.range >= 2 ? 1.0D + (this.range - 1) * 0.25D : 1.0D);
            List<LivingEntity> entities = world.getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(range, 0.25D, range));

            for (LivingEntity aoeEntity : entities) {
                if (aoeEntity != player && aoeEntity != entity && !player.isAlliedTo(entity)) {
                    DamageSource source = DamageSource.playerAttack(player);
                    float attackDamage = this.getAttackDamage() * 0.67F;
                    boolean success = ForgeHooks.onLivingAttack(aoeEntity, source, attackDamage);

                    if (success) {
                        aoeEntity.knockback(0.4F, Mth.sin(player.yRot * 0.017453292F), -Mth.cos(player.yRot * 0.017453292F));
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

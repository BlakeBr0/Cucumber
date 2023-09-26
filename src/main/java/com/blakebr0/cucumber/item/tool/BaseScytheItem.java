package com.blakebr0.cucumber.item.tool;

import com.blakebr0.cucumber.Cucumber;
import com.blakebr0.cucumber.event.ScytheHarvestCropEvent;
import com.blakebr0.cucumber.iface.IEnableable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
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
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

public class BaseScytheItem extends SwordItem {
    private static final Set<Block> ERRORED_BLOCKS = new HashSet<>();
    private static final Method GET_SEED;

    private final float attackDamage;
    private final float attackSpeed;
    private final int range;

    static {
        GET_SEED = ObfuscationReflectionHelper.findMethod(CropBlock.class, "m_6404_");
    }

    public BaseScytheItem(Tier tier, int attackDamage, float attackSpeed,  int range, Function<Properties, Properties> properties) {
        super(tier, attackDamage, attackSpeed, properties.apply(new Properties()));
        this.attackDamage = attackDamage;
        this.attackSpeed = attackSpeed;
        this.range = range;
    }

    @Override
    public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) {
        if (this instanceof IEnableable enableable) {
            if (enableable.isEnabled())
                super.fillItemCategory(group, items);
        } else {
            super.fillItemCategory(group, items);
        }
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        var player = context.getPlayer();

        if (player == null)
            return InteractionResult.FAIL;

        var pos = context.getClickedPos();
        var hand = context.getHand();
        var face = context.getClickedFace();
        var stack = player.getItemInHand(hand);

        if (!player.mayUseItemAt(pos.relative(face), face, stack))
            return InteractionResult.FAIL;

        var level = context.getLevel();

        if (level.isClientSide())
            return InteractionResult.SUCCESS;

        BlockPos.betweenClosed(pos.offset(-this.range, 0, -this.range), pos.offset(this.range, 0, this.range)).forEach(aoePos -> {
            if (stack.isEmpty())
                return;

            var state = level.getBlockState(aoePos);
            var event = new ScytheHarvestCropEvent(level, aoePos, state, stack, player);

            if (MinecraftForge.EVENT_BUS.post(event))
                return;

            var block = state.getBlock();

            if (block instanceof CropBlock crop) {
                Item seed = getSeed(crop);

                if (crop.isMaxAge(state) && seed != null) {
                    var drops = Block.getDrops(state, (ServerLevel) level, aoePos, level.getBlockEntity(aoePos));

                    for (var drop : drops) {
                        var item = drop.getItem();

                        if (!drop.isEmpty() && item == seed) {
                            drop.shrink(1);
                            break;
                        }
                    }

                    for (var drop : drops) {
                        if (!drop.isEmpty()) {
                            Block.popResource(level, aoePos, drop);
                        }
                    }

                    stack.hurtAndBreak(1, player, entity -> {
                        entity.broadcastBreakEvent(player.getUsedItemHand());
                    });

                    level.setBlockAndUpdate(aoePos, crop.getStateForAge(0));
                }
            }
        });

        return InteractionResult.SUCCESS;
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
        if (player.getAttackStrengthScale(0.5F) >= 0.95F) {
            var level = player.level;
            var range = (this.range >= 2 ? 1.0D + (this.range - 1) * 0.25D : 1.0D);
            var entities = level.getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(range, 0.25D, range));

            for (var aoeEntity : entities) {
                if (aoeEntity != player && aoeEntity != entity && !player.isAlliedTo(entity)) {
                    var source = DamageSource.playerAttack(player);
                    var attackDamage = this.getAttackDamage() * 0.67F;
                    var success = ForgeHooks.onLivingAttack(aoeEntity, source, attackDamage);

                    if (success) {
                        aoeEntity.knockback(0.4F, Mth.sin(player.getYRot() * 0.017453292F), -Mth.cos(player.getYRot() * 0.017453292F));
                        aoeEntity.hurt(source, attackDamage);
                    }
                }
            }

            level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.PLAYER_ATTACK_SWEEP, player.getSoundSource(), 1.0F, 1.0F);

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
            if (ERRORED_BLOCKS.add(block)) {
                Cucumber.LOGGER.error("Unable to get seed from crop {}", e.getLocalizedMessage());
            }
        }

        return null;
    }
}

package com.blakebr0.cucumber.item.tool;

import com.blakebr0.cucumber.event.ScytheHarvestCropEvent;
import com.blakebr0.cucumber.helper.CropHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.NetherWartBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.MinecraftForge;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;

public class BaseScytheItem extends SwordItem {
    private final float attackDamage;
    private final float attackSpeed;
    private final int range;

    public BaseScytheItem(Tier tier, int range) {
        this(tier, range, p -> p);
    }

    public BaseScytheItem(Tier tier, int range, Function<Properties, Properties> properties) {
        super(tier, 4, -2.8F, properties.apply(new Properties()));
        this.attackDamage = 4F;
        this.attackSpeed = -2.8F;
        this.range = range;
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

        var harvested = new AtomicBoolean();

        BlockPos.betweenClosed(pos.offset(-this.range, 0, -this.range), pos.offset(this.range, 0, this.range)).forEach(aoePos -> {
            if (stack.isEmpty())
                return;

            var state = level.getBlockState(aoePos);
            var event = new ScytheHarvestCropEvent(level, aoePos, state, stack, player);

            if (MinecraftForge.EVENT_BUS.post(event))
                return;

            var block = state.getBlock();

            if (block instanceof CropBlock crop) {
                var seed = CropHelper.getSeedsItem(crop);

                if (crop.isMaxAge(state) && seed != null) {
                    handleDrops(state, level, aoePos, seed);

                    stack.hurtAndBreak(1, player, entity -> {
                        entity.broadcastBreakEvent(player.getUsedItemHand());
                    });

                    level.setBlockAndUpdate(aoePos, crop.getStateForAge(0));

                    harvested.set(true);
                }
            }

            if (block instanceof NetherWartBlock && state.getValue(NetherWartBlock.AGE) == 3) {
                handleDrops(state, level, aoePos, Items.NETHER_WART);

                stack.hurtAndBreak(1, player, entity -> {
                    entity.broadcastBreakEvent(player.getUsedItemHand());
                });

                level.setBlockAndUpdate(aoePos, state.setValue(NetherWartBlock.AGE, 0));

                harvested.set(true);
            }
        });

        if (harvested.get()) {
            level.playSound(null, pos, SoundEvents.CROP_BREAK, SoundSource.BLOCKS, 1.0F, 1.0F);
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
        if (player.getAttackStrengthScale(0.5F) >= 0.95F) {
            var level = player.level();
            var range = (this.range >= 2 ? 1.0D + (this.range - 1) * 0.25D : 1.0D);
            var entities = level.getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(range, 0.25D, range));
            var damageType = level.registryAccess().lookup(Registries.DAMAGE_TYPE).map(r -> r.get(DamageTypes.PLAYER_ATTACK));

            for (var aoeEntity : entities) {
                if (aoeEntity != player && aoeEntity != entity && !player.isAlliedTo(entity)) {
                    if (damageType.isPresent() && damageType.get().isPresent()) {
                        var source = new DamageSource(damageType.get().get(), player);
                        var attackDamage = this.getAttackDamage() * 0.67F;
                        var success = ForgeHooks.onLivingAttack(aoeEntity, source, attackDamage);

                        if (success) {
                            aoeEntity.knockback(0.4F, Mth.sin(player.getYRot() * 0.017453292F), -Mth.cos(player.getYRot() * 0.017453292F));
                            aoeEntity.hurt(source, attackDamage);
                        }
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

    private static void handleDrops(BlockState state, Level level, BlockPos pos, ItemLike seed) {
        var drops = Block.getDrops(state, (ServerLevel) level, pos, level.getBlockEntity(pos));

        for (var drop : drops) {
            var item = drop.getItem();

            if (!drop.isEmpty() && item == seed) {
                drop.shrink(1);
                break;
            }
        }

        for (var drop : drops) {
            if (!drop.isEmpty()) {
                Block.popResource(level, pos, drop);
            }
        }
    }
}

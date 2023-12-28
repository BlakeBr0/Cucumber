package com.blakebr0.cucumber.item;

import com.blakebr0.cucumber.helper.NBTHelper;
import com.blakebr0.cucumber.lib.Tooltips;
import com.blakebr0.cucumber.util.Utils;
import com.blakebr0.cucumber.util.WateringCanUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.FakePlayer;

import java.util.List;
import java.util.function.Consumer;

public class BaseWateringCanItem extends BaseItem {
    protected final int range;
    protected final double chance;

    public BaseWateringCanItem(int range, double chance) {
        super(p -> p.stacksTo(1));
        this.range = range;
        this.chance = chance;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 200;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        var stack = player.getItemInHand(hand);

        if (NBTHelper.getBoolean(stack, "Water")) {
            return new InteractionResultHolder<>(InteractionResult.PASS, stack);
        }

        var trace = getPlayerPOVHitResult(level, player, ClipContext.Fluid.SOURCE_ONLY);

        if (trace.getType() != HitResult.Type.BLOCK) {
            return new InteractionResultHolder<>(InteractionResult.PASS, stack);
        }

        var pos = trace.getBlockPos();
        var direction = trace.getDirection();

        if (level.mayInteract(player, pos) && player.mayUseItemAt(pos.relative(direction), direction, stack)) {
            var fluid = level.getFluidState(pos);

            if (fluid.is(FluidTags.WATER)) {
                NBTHelper.setBoolean(stack, "Water", true);

                player.playSound(SoundEvents.BUCKET_FILL, 1.0F, 1.0F);

                return new InteractionResultHolder<>(InteractionResult.SUCCESS, stack);
            }
        }

        return new InteractionResultHolder<>(InteractionResult.PASS, stack);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        var player = context.getPlayer();
        if (player == null)
            return InteractionResult.FAIL;

        var hand = context.getHand();
        var stack = player.getItemInHand(hand);
        var pos = context.getClickedPos();
        var direction = context.getClickedFace();

        if (!player.mayUseItemAt(pos.relative(direction), direction, stack))
            return InteractionResult.FAIL;

        if (!NBTHelper.getBoolean(stack, "Water"))
            return InteractionResult.PASS;

        player.startUsingItem(hand);

        return InteractionResult.PASS;
    }

    @Override
    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
        var player = context.getPlayer();

        if (player instanceof FakePlayer) {
            var level = context.getLevel();
            var pos = context.getClickedPos();
            var direction = context.getClickedFace();

            return this.doWater(stack, level, player, pos, direction);
        }

        return InteractionResult.PASS;
    }

    @Override
    public void onUseTick(Level level, LivingEntity entity, ItemStack stack, int remainingTicks) {
        if (remainingTicks >= 0 && entity instanceof Player player) {
            var trace = getPlayerPOVHitResult(level, player, ClipContext.Fluid.ANY);

            if (trace.getType() == HitResult.Type.BLOCK) {
                this.doWater(stack, level, player, trace.getBlockPos(), trace.getDirection());
            } else {
                entity.releaseUsingItem();
            }
        } else {
            entity.releaseUsingItem();
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag advanced) {
        if (NBTHelper.getBoolean(stack, "Water")) {
            tooltip.add(Tooltips.FILLED.build());
        } else {
            tooltip.add(Tooltips.EMPTY.build());
        }
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(ItemRenderProperties.INSTANCE);
    }

    protected InteractionResult doWater(ItemStack stack, Level level, Player player, BlockPos pos, Direction direction) {
        if (player == null)
            return InteractionResult.FAIL;

        if (!player.mayUseItemAt(pos.relative(direction), direction, stack))
            return InteractionResult.FAIL;

        if (!NBTHelper.getBoolean(stack, "Water"))
            return InteractionResult.FAIL;

        if (!this.allowFakePlayerWatering() && player instanceof FakePlayer)
            return InteractionResult.FAIL;

        if (!level.isClientSide()) {
            var cooldowns = player.getCooldowns();
            var item = stack.getItem();

            if (!cooldowns.isOnCooldown(item)) {
                cooldowns.addCooldown(item, getThrottleTicks(player));
            } else {
                return InteractionResult.FAIL;
            }
        }

        int range = (this.range - 1) / 2;

        BlockPos.betweenClosedStream(pos.offset(-range, -range, -range), pos.offset(range, range, range)).forEach(aoePos -> {
            var aoeState = level.getBlockState(aoePos);
            if (aoeState.getBlock() instanceof FarmBlock) {
                int moisture = aoeState.getValue(FarmBlock.MOISTURE);
                if (moisture < 7) {
                    level.setBlock(aoePos, aoeState.setValue(FarmBlock.MOISTURE, 7), 3);
                }
            }
        });

        var random = Utils.RANDOM;

        for (int x = -range; x <= range; x++) {
            for (int z = -range; z <= range; z++) {
                double d0 = pos.offset(x, 0, z).getX() + random.nextFloat();
                double d1 = pos.offset(x, 0, z).getY() + 1.0D;
                double d2 = pos.offset(x, 0, z).getZ() + random.nextFloat();

                var state = level.getBlockState(pos);
                if (state.canOcclude() || state.getBlock() instanceof FarmBlock)
                    d1 += 0.3D;

                level.addParticle(ParticleTypes.RAIN, d0, d1, d2, 0.0D, 0.0D, 0.0D);
            }
        }

        startPlayingSound(player);

        if (!level.isClientSide()) {
            if (Math.random() <= this.chance) {
                BlockPos.betweenClosedStream(pos.offset(-range, -range, -range), pos.offset(range, range, range)).forEach(aoePos -> {
                    var state = level.getBlockState(aoePos);
                    var plantBlock = state.getBlock();

                    if (plantBlock instanceof BonemealableBlock || plantBlock instanceof IPlantable || plantBlock == Blocks.MYCELIUM || plantBlock == Blocks.CHORUS_FLOWER) {
                        state.randomTick((ServerLevel) level, aoePos, random);
                    }
                });

                return InteractionResult.FAIL;
            }
        }

        return InteractionResult.FAIL;
    }

    protected boolean allowFakePlayerWatering() {
        return true;
    }

    /**
     * Starts playing the watering can sound for the given player on the client side
     * @param player the player
     */
    public static void startPlayingSound(Player player) {
        if (player.level().isClientSide()) {
            WateringCanUtil.startPlayingSound(player);
        }
    }

    /**
     * Stops playing the watering can sound for the given player on the client side
     * @param player the player
     */
    public static void stopPlayingSound(Player player) {
        if (player.level().isClientSide()) {
            WateringCanUtil.stopPlayingSound(player);
        }
    }

    private static int getThrottleTicks(Player player) {
        return player instanceof FakePlayer ? 10 : 5;
    }

    static class ItemRenderProperties implements IClientItemExtensions {
        public static final IClientItemExtensions INSTANCE = new ItemRenderProperties();

        @Override
        public boolean applyForgeHandTransform(PoseStack matrix, LocalPlayer player, HumanoidArm arm, ItemStack stack, float partialTick, float equipProcess, float swingProcess) {
            if (player.isUsingItem() && player.getUseItemRemainingTicks() > 0) {
                float f = (float) (player.getUseItemRemainingTicks() % 20);
                float f1 = f - partialTick + 1.0F;
                float f2 = 1.0F - f1 / 20.0F;
                float f7 = -10.0F + 10.0F * Mth.cos(f2 * 2.0F * (float) Math.PI);

                matrix.mulPose(Axis.XP.rotationDegrees(f7));
            }

            return false;
        }
    }
}

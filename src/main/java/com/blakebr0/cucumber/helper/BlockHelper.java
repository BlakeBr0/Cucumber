package com.blakebr0.cucumber.helper;

import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BlockEvent;

/*
 * Parts of the code used in this class is derived from Actually Additions,
 * by Ellpeck (https://github.com/Ellpeck/ActuallyAdditions)
 * Or Draconic Evolution, by brandon3055 (https://github.com/brandon3055/Draconic-Evolution)
 */
public final class BlockHelper {
	private static BlockHitResult rayTraceBlocks(Level level, Player player, double reach, ClipContext.Fluid fluidMode) {
        var pitch = player.getXRot();
        var yaw = player.getYRot();
        var eyePos = player.getEyePosition(1.0F);
        var f2 = Mth.cos(-yaw * ((float) Math.PI / 180F) - (float) Math.PI);
        var f3 = Mth.sin(-yaw * ((float) Math.PI / 180F) - (float) Math.PI);
        var f4 = -Mth.cos(-pitch * ((float) Math.PI / 180F));
        var f5 = Mth.sin(-pitch * ((float) Math.PI / 180F));
        var f6 = f3 * f4;
        var f7 = f2 * f4;

        var vec3d1 = eyePos.add((double) f6 * reach, (double) f5 * reach, (double) f7 * reach);

        return level.clip(new ClipContext(eyePos, vec3d1, ClipContext.Block.OUTLINE, fluidMode, player));
	}

	public static BlockHitResult rayTraceBlocks(Level level, Player player) {
		return rayTraceBlocks(level, player, ClipContext.Fluid.NONE);
	}

	public static BlockHitResult rayTraceBlocks(Level level, Player player, ClipContext.Fluid fluidMode) {
        var attribute = player.getAttribute(ForgeMod.REACH_DISTANCE.get());
        var reach = attribute != null ? attribute.getValue() : 5.0D;

		return rayTraceBlocks(level, player, reach, fluidMode);
	}

	public static boolean breakBlocksAOE(ItemStack stack, Level level, Player player, BlockPos pos) {
	    return breakBlocksAOE(stack, level, player, pos, true);
    }

    public static boolean breakBlocksAOE(ItemStack stack, Level level, Player player, BlockPos pos, boolean playEvent) {
        if (level.isEmptyBlock(pos))
            return false;

        if (!level.isClientSide() && player instanceof ServerPlayer mplayer) {
            var state = level.getBlockState(pos);
            var block = state.getBlock();

            var event = new BlockEvent.BreakEvent(level, pos, state, mplayer);
            if (MinecraftForge.EVENT_BUS.post(event))
                return false;

            if (playEvent) {
                level.levelEvent(2001, pos, Block.getId(state));
            }

            boolean changed = level.setBlockAndUpdate(pos, state.getFluidState().createLegacyBlock());
            if (changed) {
                if (state.is(BlockTags.GUARDED_BY_PIGLINS)) {
                    PiglinAi.angerNearbyPiglins(player, false);
                }

                if (!player.getAbilities().instabuild) {
                    var tile = level.getBlockEntity(pos);

                    block.destroy(level, pos, state);
                    block.playerDestroy(level, player, pos, state, tile, stack);
                    block.popExperience((ServerLevel) level, pos, event.getExpToDrop());
                }

                stack.mineBlock(level, state, pos, player);
            }

            mplayer.connection.send(new ClientboundBlockUpdatePacket(level, pos));

            return true;
        }

        return false;
    }
}

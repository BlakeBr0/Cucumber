package com.blakebr0.cucumber.helper;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.util.Mth;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BlockEvent;

/*
 * Parts of the code used in this class is derived from Actually Additions,
 * by Ellpeck (https://github.com/Ellpeck/ActuallyAdditions)
 * Or Draconic Evolution, by brandon3055
 * (https://github.com/brandon3055/Draconic-Evolution)
 */
public final class BlockHelper {
	private static BlockHitResult rayTraceBlocks(Level world, Player player, double reach, ClipContext.Fluid fluidMode) {
        float pitch = player.xRot;
        float yaw = player.yRot;
        Vec3 eyePos = player.getEyePosition(1.0F);
        float f2 = Mth.cos(-yaw * ((float) Math.PI / 180F) - (float) Math.PI);
        float f3 = Mth.sin(-yaw * ((float) Math.PI / 180F) - (float) Math.PI);
        float f4 = -Mth.cos(-pitch * ((float) Math.PI / 180F));
        float f5 = Mth.sin(-pitch * ((float) Math.PI / 180F));
        float f6 = f3 * f4;
        float f7 = f2 * f4;

        Vec3 vec3d1 = eyePos.add((double) f6 * reach, (double) f5 * reach, (double) f7 * reach);
        return world.clip(new ClipContext(eyePos, vec3d1, ClipContext.Block.OUTLINE, fluidMode, player));
	}

	public static BlockHitResult rayTraceBlocks(Level world, Player player) {
		return rayTraceBlocks(world, player, ClipContext.Fluid.NONE);
	}

	public static BlockHitResult rayTraceBlocks(Level world, Player player, ClipContext.Fluid fluidMode) {
        AttributeInstance attribute = player.getAttribute(ForgeMod.REACH_DISTANCE.get());
        double reach = attribute != null ? attribute.getValue() : 5.0D;
		return rayTraceBlocks(world, player, reach, fluidMode);
	}

	public static boolean breakBlocksAOE(ItemStack stack, Level world, Player player, BlockPos pos) {
	    return breakBlocksAOE(stack, world, player, pos, true);
    }

    public static boolean breakBlocksAOE(ItemStack stack, Level world, Player player, BlockPos pos, boolean playEvent) {
        if (world.isEmptyBlock(pos))
            return false;

        if (!world.isClientSide() && player instanceof ServerPlayer) {
            ServerPlayer mplayer = (ServerPlayer) player;
            BlockState state = world.getBlockState(pos);
            Block block = state.getBlock();

            BlockEvent.BreakEvent event = new BlockEvent.BreakEvent(world, pos, state, mplayer);
            if (MinecraftForge.EVENT_BUS.post(event))
                return false;

            if (playEvent) {
                world.levelEvent(2001, pos, Block.getId(state));
            }

            boolean changed = world.setBlockAndUpdate(pos, state.getFluidState().createLegacyBlock());
            if (changed) {
                if (block.is(BlockTags.GUARDED_BY_PIGLINS)) {
                    PiglinAi.angerNearbyPiglins(player, false);
                }

                if (!player.abilities.instabuild) {
                    BlockEntity tile = world.getBlockEntity(pos);

                    block.destroy(world, pos, state);
                    block.playerDestroy(world, player, pos, state, tile, stack);
                    block.popExperience((ServerLevel) world, pos, event.getExpToDrop());
                }

                stack.mineBlock(world, state, pos, player);
            }

            mplayer.connection.send(new ClientboundBlockUpdatePacket(world, pos));

            return true;
        }

        return false;
    }
}

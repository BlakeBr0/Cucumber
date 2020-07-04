package com.blakebr0.cucumber.util;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPlayerDiggingPacket;
import net.minecraft.network.play.server.SChangeBlockPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

// TODO: 1.16: reevaluate
public class ToolTools {
	/*
	 * Parts of the code used in this class is derived from Actually Additions,
	 * by Ellpeck (https://github.com/Ellpeck/ActuallyAdditions) Draconic
	 * Evolution, by brandon3055
	 * (https://github.com/brandon3055/Draconic-Evolution) I do not claim to own
	 * or have created any of the code that came from those mods
	 */
	// TODO Raytrace stuff
//	private static RayTraceResult getPosWithinReach(World world, PlayerEntity player, double distance, raytr fluidMode, boolean p1, boolean p2) {
//		float f = player.rotationPitch;
//		float f1 = player.rotationYaw;
//		double d0 = player.posX;
//		double d1 = player.posY + (double) player.getEyeHeight();
//		double d2 = player.posZ;
//		Vec3d vec = new Vec3d(d0, d1, d2);
//		float f2 = MathHelper.cos(-f1 * 0.017453292F - (float) Math.PI);
//		float f3 = MathHelper.sin(-f1 * 0.017453292F - (float) Math.PI);
//		float f4 = -MathHelper.cos(-f * 0.017453292F);
//		float f5 = MathHelper.sin(-f * 0.017453292F);
//		float f6 = f3 * f4;
//		float f7 = f2 * f4;
//		Vec3d vec1 = vec.add((double) f6 * distance, (double) f5 * distance, (double) f7 * distance);
//		return world.rayTraceBlocks(vec, vec1, fluidMode, p1, p2);
//	}
//
//	public static RayTraceResult getBlockWithinReach(World world, PlayerEntity player) {
//		return getBlockWithinReach(world, player, RayTraceFluidMode.NEVER, true, false);
//	}
//
//	public static RayTraceResult getBlockWithinReach(World world, PlayerEntity player, RayTraceFluidMode fluidMode, boolean ignoreBlockWithoutBoundingBox, boolean returnLastUncollidableBlock) {
//		return getPosWithinReach(world, player, player.getAttribute(PlayerEntity.REACH_DISTANCE).getValue(), fluidMode, ignoreBlockWithoutBoundingBox, returnLastUncollidableBlock);
//	}

	public static boolean breakBlocksAOE(ItemStack stack, World world, PlayerEntity player, BlockPos pos) {
		if (world.isAirBlock(pos))
			return false;

		BlockState state = world.getBlockState(pos);
		Block block = state.getBlock();

		if (!world.isRemote) {
			world.playEvent(player, 2001, pos, Block.getStateId(state));
		} else {
			world.playEvent(2001, pos, Block.getStateId(state));
		}

		if (player.abilities.isCreativeMode) {
			block.onBlockHarvested(world, pos, state, player);
			if (block.removedByPlayer(state, world, pos, player, false, state.getFluidState())) {
				block.onPlayerDestroy(world, pos, state);
			}

			if (!world.isRemote) {
				if (player instanceof ServerPlayerEntity) {
					((ServerPlayerEntity) player).connection.sendPacket(new SChangeBlockPacket(world, pos));
				}
			}
			
			return true;
		}

		stack.onBlockDestroyed(world, state, pos, player);

		if (!world.isRemote) {
			if (player instanceof ServerPlayerEntity) {
				ServerPlayerEntity mplayer = (ServerPlayerEntity) player;

				int xp = ForgeHooks.onBlockBreakEvent(world, mplayer.interactionManager.getGameType(), mplayer, pos);
				if (xp == -1) return false;

				TileEntity tile = world.getTileEntity(pos);
				if (block.removedByPlayer(state, world, pos, player, true, state.getFluidState())) {
					block.onPlayerDestroy(world, pos, state);
					block.harvestBlock(world, player, pos, state, tile, stack);
					block.dropXpOnBlockBreak(world, pos, xp);
				}

				mplayer.connection.sendPacket(new SChangeBlockPacket(world, pos));
				return true;
			}
		} else {
			if (block.removedByPlayer(state, world, pos, player, true, state.getFluidState())) {
				block.onPlayerDestroy(world, pos, state);
			}

			// TODO Figure out how to get whatever direction this is looking for
//			Minecraft.getInstance().getConnection().sendPacket(new CPlayerDiggingPacket(CPlayerDiggingPacket.Action.STOP_DESTROY_BLOCK, pos, Minecraft.getInstance().objectMouseOver));

			return true;
		}
		
		return false;
	}
}
package com.blakebr0.cucumber.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public final class VanillaPacketDispatcher {
	public static void dispatchTEToNearbyPlayers(TileEntity tile) {
		World world = tile.getWorld();
		if (world == null)
			return;

		SUpdateTileEntityPacket packet = tile.getUpdatePacket();
		if (packet == null)
			return;

		List<? extends PlayerEntity> players = world.getPlayers();
		BlockPos pos = tile.getPos();
		for (Object player : players) {
			if (player instanceof ServerPlayerEntity) {
				ServerPlayerEntity mPlayer = (ServerPlayerEntity) player;
				if (pointDistancePlane(mPlayer.getPosX(), mPlayer.getPosZ(), pos.getX() + 0.5, pos.getZ() + 0.5) < 64) {
					mPlayer.connection.sendPacket(packet);
				}
			}
		}
	}

	public static void dispatchTEToNearbyPlayers(World world, int x, int y, int z) {
		TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));
		if (tile != null) {
			dispatchTEToNearbyPlayers(tile);
		}
	}

	public static float pointDistancePlane(double x1, double y1, double x2, double y2) {
		return (float) Math.hypot(x1 - x2, y1 - y2);
	}
}

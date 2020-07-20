package com.blakebr0.cucumber.helper;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public final class TileEntityHelper {
    public static void dispatchToNearbyPlayers(TileEntity tile) {
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
                if (isPlayerNearby(mPlayer.getPosX(), mPlayer.getPosZ(), pos.getX() + 0.5, pos.getZ() + 0.5)) {
                    mPlayer.connection.sendPacket(packet);
                }
            }
        }
    }

    public static void dispatchToNearbyPlayers(World world, int x, int y, int z) {
        TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));
        if (tile != null) {
            dispatchToNearbyPlayers(tile);
        }
    }

    private static boolean isPlayerNearby(double x1, double z1, double x2, double z2) {
        return Math.hypot(x1 - x2, z1 - z2) < 64;
    }
}

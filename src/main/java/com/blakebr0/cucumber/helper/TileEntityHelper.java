package com.blakebr0.cucumber.helper;

import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

import java.util.List;

public final class TileEntityHelper {
    public static void dispatchToNearbyPlayers(BlockEntity tile) {
        Level world = tile.getLevel();
        if (world == null)
            return;

        ClientboundBlockEntityDataPacket packet = tile.getUpdatePacket();
        if (packet == null)
            return;

        List<? extends Player> players = world.players();
        BlockPos pos = tile.getBlockPos();
        for (Object player : players) {
            if (player instanceof ServerPlayer) {
                ServerPlayer mPlayer = (ServerPlayer) player;
                if (isPlayerNearby(mPlayer.getX(), mPlayer.getZ(), pos.getX() + 0.5, pos.getZ() + 0.5)) {
                    mPlayer.connection.send(packet);
                }
            }
        }
    }

    public static void dispatchToNearbyPlayers(Level world, int x, int y, int z) {
        BlockEntity tile = world.getBlockEntity(new BlockPos(x, y, z));
        if (tile != null) {
            dispatchToNearbyPlayers(tile);
        }
    }

    private static boolean isPlayerNearby(double x1, double z1, double x2, double z2) {
        return Math.hypot(x1 - x2, z1 - z2) < 64;
    }
}

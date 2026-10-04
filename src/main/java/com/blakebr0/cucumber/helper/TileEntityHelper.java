package com.blakebr0.cucumber.helper;

import com.blakebr0.cucumber.tileentity.BaseTileEntity;
import cpw.mods.modlauncher.api.INameMappingService;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public final class TileEntityHelper {
    private static final ClassValue<Boolean> DEFAULT_UPDATE_PACKET = new ClassValue<>() {
        @Override
        protected Boolean computeValue(Class<?> type) {
            try {
                var name = ObfuscationReflectionHelper.remapName(INameMappingService.Domain.METHOD, "m_58483_");
                var owner = type.getMethod(name).getDeclaringClass();
                return owner == BlockEntity.class || owner == BaseTileEntity.class
                        && type.getMethod("getUpdatePacket").getDeclaringClass() == BaseTileEntity.class;
            } catch (ReflectiveOperationException | SecurityException e) {
                return false;
            }
        }
    };

    public static void dispatchToNearbyPlayers(BlockEntity tile) {
        var level = tile.getLevel();
        if (level == null)
            return;

        if (DEFAULT_UPDATE_PACKET.get(tile.getClass())) {
            var pos = tile.getBlockPos();
            boolean nearby = false;
            for (var player : level.players()) {
                if (player instanceof ServerPlayer && isPlayerNearby(player.getX(), player.getZ(), pos.getX() + 0.5, pos.getZ() + 0.5)) {
                    nearby = true;
                    break;
                }
            }
            if (!nearby)
                return;
        }

        var packet = tile.getUpdatePacket();
        if (packet == null)
            return;

        var players = level.players();
        var pos = tile.getBlockPos();

        for (var player : players) {
            if (player instanceof ServerPlayer mPlayer) {
                if (isPlayerNearby(mPlayer.getX(), mPlayer.getZ(), pos.getX() + 0.5, pos.getZ() + 0.5)) {
                    mPlayer.connection.send(packet);
                }
            }
        }
    }

    public static void dispatchToNearbyPlayers(Level level, int x, int y, int z) {
        var tile = level.getBlockEntity(new BlockPos(x, y, z));
        if (tile != null) {
            dispatchToNearbyPlayers(tile);
        }
    }

    private static boolean isPlayerNearby(double x1, double z1, double x2, double z2) {
        return Math.hypot(x1 - x2, z1 - z2) < 64;
    }
}

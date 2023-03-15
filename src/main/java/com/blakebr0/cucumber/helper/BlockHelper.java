package com.blakebr0.cucumber.helper;

import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.ForgeMod;

public final class BlockHelper {
    public static BlockHitResult rayTraceBlocks(Level level, Player player) {
        return rayTraceBlocks(level, player, ClipContext.Fluid.NONE);
    }

    public static BlockHitResult rayTraceBlocks(Level level, Player player, ClipContext.Fluid fluidMode) {
        var attribute = player.getAttribute(ForgeMod.REACH_DISTANCE.get());
        var reach = attribute != null ? attribute.getValue() : 4.5D;

        return rayTraceBlocks(level, player, reach, fluidMode);
    }

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

    public static boolean harvestBlock(ItemStack stack, Level level, ServerPlayer player, BlockPos pos) {
        return harvestBlock(stack, level, player, pos, true);
    }

    public static boolean harvestBlock(ItemStack stack, Level level, ServerPlayer player, BlockPos pos, boolean playEvent) {
        if (level.isClientSide())
            return true;

        var type = player.gameMode.getGameModeForPlayer();

        int exp = ForgeHooks.onBlockBreakEvent(level, type, player, pos);
        if (exp == -1) {
            return false;
        }

        if (player.blockActionRestricted(level, pos, type))
            return false;

        var state = level.getBlockState(pos);

        if (playEvent) {
            level.levelEvent(2001, pos, Block.getId(state));
        }

        var destroyed = destroyBlock(state, level, player, pos);

        if (player.isCreative())
            return true;

        var block = state.getBlock();
        if (destroyed && state.canHarvestBlock(level, pos, player)) {
            block.playerDestroy(level, player, pos, state, level.getBlockEntity(pos), stack);
            stack.mineBlock(level, state, pos, player);
        }

        if (destroyed && exp > 0) {
            block.popExperience(player.getLevel(), pos, exp);
        }

        return true;
    }

    public static boolean harvestAOEBlock(ItemStack stack, Level level, ServerPlayer player, BlockPos pos) {
        if (harvestBlock(stack, level, player, pos, false)) {
            player.connection.send(new ClientboundBlockUpdatePacket(level, pos));
            return true;
        }

        return false;
    }

    public static boolean destroyBlock(BlockState state, Level level, Player player, BlockPos pos) {
        var canHarvest = !player.isCreative() && state.canHarvestBlock(level, pos, player);
        var destroyed = state.onDestroyedByPlayer(level, pos, player, canHarvest, level.getFluidState(pos));

        if (destroyed) {
            state.getBlock().destroy(level, pos, state);
        }

        return destroyed;
    }
}

package com.blakebr0.cucumber.helper;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.monster.piglin.PiglinTasks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SChangeBlockPacket;
import net.minecraft.tags.BlockTags;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.ForgeMod;

/*
 * Parts of the code used in this class is derived from Actually Additions,
 * by Ellpeck (https://github.com/Ellpeck/ActuallyAdditions)
 * Or Draconic Evolution, by brandon3055
 * (https://github.com/brandon3055/Draconic-Evolution)
 */
public final class BlockHelper {
	private static BlockRayTraceResult rayTraceBlocks(World world, PlayerEntity player, double reach, RayTraceContext.FluidMode fluidMode) {
        float pitch = player.rotationPitch;
        float yaw = player.rotationYaw;
        Vector3d eyePos = player.getEyePosition(1.0F);
        float f2 = MathHelper.cos(-yaw * ((float) Math.PI / 180F) - (float) Math.PI);
        float f3 = MathHelper.sin(-yaw * ((float) Math.PI / 180F) - (float) Math.PI);
        float f4 = -MathHelper.cos(-pitch * ((float) Math.PI / 180F));
        float f5 = MathHelper.sin(-pitch * ((float) Math.PI / 180F));
        float f6 = f3 * f4;
        float f7 = f2 * f4;

        Vector3d vec3d1 = eyePos.add((double) f6 * reach, (double) f5 * reach, (double) f7 * reach);
        return world.rayTraceBlocks(new RayTraceContext(eyePos, vec3d1, RayTraceContext.BlockMode.OUTLINE, fluidMode, player));
	}

	public static BlockRayTraceResult rayTraceBlocks(World world, PlayerEntity player) {
		return rayTraceBlocks(world, player, RayTraceContext.FluidMode.NONE);
	}

	public static BlockRayTraceResult rayTraceBlocks(World world, PlayerEntity player, RayTraceContext.FluidMode fluidMode) {
        ModifiableAttributeInstance attribute = player.getAttribute(ForgeMod.REACH_DISTANCE.get());
        double reach = attribute != null ? attribute.getValue() : 5.0D;
		return rayTraceBlocks(world, player, reach, fluidMode);
	}

	public static boolean breakBlocksAOE(ItemStack stack, World world, PlayerEntity player, BlockPos pos) {
	    return breakBlocksAOE(stack, world, player, pos, true);
    }

    public static boolean breakBlocksAOE(ItemStack stack, World world, PlayerEntity player, BlockPos pos, boolean playEvent) {
        if (world.isAirBlock(pos))
            return false;

        BlockState state = world.getBlockState(pos);
        Block block = state.getBlock();

        if (!world.isRemote()) {
            if (player instanceof ServerPlayerEntity) {
                ServerPlayerEntity mplayer = (ServerPlayerEntity) player;

                int xp = ForgeHooks.onBlockBreakEvent(world, mplayer.interactionManager.getGameType(), mplayer, pos);
                if (xp == -1) return false;

                if (playEvent) {
                    world.playEvent(player, 2001, pos, Block.getStateId(state));
                }

                TileEntity tile = world.getTileEntity(pos);
                if (world.setBlockState(pos, state.getFluidState().getBlockState(), 3)) {
                    if (block.isIn(BlockTags.GUARDED_BY_PIGLINS)) {
                        PiglinTasks.func_234478_a_(player, false);
                    }

                    if (!player.abilities.isCreativeMode) {
                        block.onPlayerDestroy(world, pos, state);
                        block.harvestBlock(world, player, pos, state, tile, stack);
                        block.dropXpOnBlockBreak((ServerWorld) world, pos, xp);
                    }

                    stack.onBlockDestroyed(world, state, pos, player);
                }

                mplayer.connection.sendPacket(new SChangeBlockPacket(world, pos));
                return true;
            }
        } else {
            stack.onBlockDestroyed(world, state, pos, player);

            return true;
        }

        return false;
    }
}

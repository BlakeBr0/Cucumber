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
	private static BlockRayTraceResult rayTraceBlocks(World world, PlayerEntity player, double reach, RayTraceContext.FluidMode fluidMode) {
        float pitch = player.xRot;
        float yaw = player.yRot;
        Vector3d eyePos = player.getEyePosition(1.0F);
        float f2 = MathHelper.cos(-yaw * ((float) Math.PI / 180F) - (float) Math.PI);
        float f3 = MathHelper.sin(-yaw * ((float) Math.PI / 180F) - (float) Math.PI);
        float f4 = -MathHelper.cos(-pitch * ((float) Math.PI / 180F));
        float f5 = MathHelper.sin(-pitch * ((float) Math.PI / 180F));
        float f6 = f3 * f4;
        float f7 = f2 * f4;

        Vector3d vec3d1 = eyePos.add((double) f6 * reach, (double) f5 * reach, (double) f7 * reach);
        return world.clip(new RayTraceContext(eyePos, vec3d1, RayTraceContext.BlockMode.OUTLINE, fluidMode, player));
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
        if (world.isEmptyBlock(pos))
            return false;

        if (!world.isClientSide() && player instanceof ServerPlayerEntity) {
            ServerPlayerEntity mplayer = (ServerPlayerEntity) player;
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
                    PiglinTasks.angerNearbyPiglins(player, false);
                }

                if (!player.abilities.instabuild) {
                    TileEntity tile = world.getBlockEntity(pos);

                    block.destroy(world, pos, state);
                    block.playerDestroy(world, player, pos, state, tile, stack);
                    block.popExperience((ServerWorld) world, pos, event.getExpToDrop());
                }

                stack.mineBlock(world, state, pos, player);
            }

            mplayer.connection.send(new SChangeBlockPacket(world, pos));

            return true;
        }

        return false;
    }
}

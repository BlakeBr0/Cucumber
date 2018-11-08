package com.blakebr0.cucumber.helper;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockHelper {

	public static boolean rotate(Block block, World world, BlockPos pos, EnumFacing axis) {
		TileEntity tile = world.getTileEntity(pos);
		boolean rotate = block.rotateBlock(world, pos, axis);
		
		if (tile != null && rotate) {
			tile.validate();
			world.setTileEntity(pos, tile);
		}
		
		return rotate;
	}
}

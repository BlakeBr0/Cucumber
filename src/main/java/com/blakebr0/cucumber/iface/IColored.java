package com.blakebr0.cucumber.iface;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReaderBase;

public interface IColored {
	int color(int index);

	class BlockColors implements IBlockColor {
		@Override
		public int getColor(IBlockState state, IWorldReaderBase world, BlockPos post, int index) {
			return ((IColored) state.getBlock()).color(index);
		}
	}

	class ItemColors implements IItemColor {
		@Override
		public int getColor(ItemStack stack, int index) {
			return ((IColored) stack.getItem()).color(index);
		}
	}

	class ItemBlockColors implements IItemColor {
		@Override
		public int getColor(ItemStack stack, int index) {
			return ((IColored) Block.getBlockFromItem(stack.getItem())).color(index);
		}
	}
}

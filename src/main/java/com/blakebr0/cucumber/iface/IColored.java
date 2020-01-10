package com.blakebr0.cucumber.iface;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ILightReader;

public interface IColored {
	int getColor(int index);

	class BlockColors implements IBlockColor {
		@Override
		public int getColor(BlockState state, ILightReader world, BlockPos pos, int index) {
			return ((IColored) state.getBlock()).getColor(index);
		}
	}

	class ItemColors implements IItemColor {
		@Override
		public int getColor(ItemStack stack, int index) {
			return ((IColored) stack.getItem()).getColor(index);
		}
	}

	class ItemBlockColors implements IItemColor {
		@Override
		public int getColor(ItemStack stack, int index) {
			return ((IColored) Block.getBlockFromItem(stack.getItem())).getColor(index);
		}
	}
}

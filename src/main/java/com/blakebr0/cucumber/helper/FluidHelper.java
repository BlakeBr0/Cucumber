package com.blakebr0.cucumber.helper;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;

public final class FluidHelper {
	public static FluidStack getFluidFromStack(ItemStack stack) {
		return FluidStack.loadFluidStackFromNBT(stack.getTag());
	}

	public static Rarity getFluidRarity(FluidStack fluid) {
		return fluid.getFluid().getAttributes().getRarity();
	}

	public static int getFluidAmount(ItemStack stack) {
		var fluid = getFluidFromStack(stack);
		return fluid == null ? 0 : fluid.getAmount();
	}

	public static ItemStack getFilledBucket(FluidStack fluid, Item bucket, int capacity) {
		if (ForgeRegistries.FLUIDS.containsKey(fluid.getFluid().getRegistryName())) {
			var filledBucket = new ItemStack(bucket);
			var fluidContents = new FluidStack(fluid, capacity);

			var tag = new CompoundTag();

			fluidContents.writeToNBT(tag);
			filledBucket.setTag(tag);

			return filledBucket;
		}

		return ItemStack.EMPTY;
	}

	public static int toBuckets(int i) {
		return i - (i % 1000);
	}
}

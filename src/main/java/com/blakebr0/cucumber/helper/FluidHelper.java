package com.blakebr0.cucumber.helper;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.nbt.CompoundNBT;
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
		FluidStack fluid = getFluidFromStack(stack);
		return fluid == null ? 0 : fluid.getAmount();
	}

	public static ItemStack getFilledBucket(FluidStack fluid, Item bucket, int capacity) {
		if (ForgeRegistries.FLUIDS.containsKey(fluid.getFluid().getRegistryName())) {
			ItemStack filledBucket = new ItemStack(bucket);
			FluidStack fluidContents = new FluidStack(fluid, capacity);

			CompoundNBT tag = new CompoundNBT();
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

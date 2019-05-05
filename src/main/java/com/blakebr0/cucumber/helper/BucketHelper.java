package com.blakebr0.cucumber.helper;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class BucketHelper {
	public static FluidStack getFluid(ItemStack stack) {
		return FluidStack.loadFluidStackFromNBT(stack.getTag());
	}

	public static int getFluidAmount(ItemStack stack) {
		FluidStack fluid = getFluid(stack);
		return fluid == null ? 0 : fluid.amount;
	}

	public static int toBuckets(int i) {
		return i - (i % Fluid.BUCKET_VOLUME);
	}
	
	public static ItemStack getFilledBucket(FluidStack fluid, Item bucket, int capacity) {
//        if (FluidRegistry.getRegisteredFluids().values().contains(fluid.getFluid())) {
//            ItemStack filledBucket = new ItemStack(bucket);
//            FluidStack fluidContents = new FluidStack(fluid, capacity);
//
//            NBTTagCompound tag = new NBTTagCompound();
//            fluidContents.writeToNBT(tag);
//            filledBucket.setTag(tag);
//
//            return filledBucket;
//        }
        
        return ItemStack.EMPTY;
	}
}

package com.blakebr0.cucumber.iface;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public interface IFluidHolder {

	int getCapacity(ItemStack stack);

	FluidStack getFluid(ItemStack stack);

	int fill(ItemStack stack, FluidStack fluid, boolean canFill);

	FluidStack drain(ItemStack stack, int amount, boolean canDrain);
}

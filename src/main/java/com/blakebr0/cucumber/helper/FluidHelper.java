package com.blakebr0.cucumber.helper;

import net.minecraft.item.EnumRarity;
import net.minecraftforge.fluids.FluidStack;

public class FluidHelper {
	public static EnumRarity getFluidRarity(FluidStack fluid) {
		return fluid.getFluid().getRarity();
	}
}

package com.blakebr0.cucumber.helper;

import net.minecraft.item.Rarity;
import net.minecraftforge.fluids.FluidStack;

public class FluidHelper {
	public static Rarity getFluidRarity(FluidStack fluid) {
		return fluid.getFluid().getAttributes().getRarity();
	}
}

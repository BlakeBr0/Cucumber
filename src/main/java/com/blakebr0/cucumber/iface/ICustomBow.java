package com.blakebr0.cucumber.iface;

import net.minecraft.item.ItemStack;

public interface ICustomBow {

	default float getDrawSpeedMulti(ItemStack stack) {
		return 1.0F;
	}
}

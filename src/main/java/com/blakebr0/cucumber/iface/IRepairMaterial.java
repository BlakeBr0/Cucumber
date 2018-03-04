package com.blakebr0.cucumber.iface;

import net.minecraft.item.ItemStack;

public interface IRepairMaterial {

	public void setRepairMaterial(ItemStack stack);

	public ItemStack getRepairMaterial();
}

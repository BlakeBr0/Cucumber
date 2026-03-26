package com.blakebr0.cucumber.inventory;

import net.minecraft.world.item.ItemStack;

@FunctionalInterface
public interface OnContentsChangedFunction {
    void apply(int slot, ItemStack oldStack);
}

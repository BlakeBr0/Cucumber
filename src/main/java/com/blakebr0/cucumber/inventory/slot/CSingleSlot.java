package com.blakebr0.cucumber.inventory.slot;

import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.transfer.item.ItemStacksResourceHandler;
import net.neoforged.neoforge.transfer.item.ResourceHandlerSlot;

public class CSingleSlot extends ResourceHandlerSlot {
	public CSingleSlot(ItemStacksResourceHandler inventory, int index, int xPosition, int yPosition) {
		super(inventory, inventory::set, index, xPosition, yPosition);
	}

	@Override
	public int getMaxStackSize() {
		return 1;
	}

	@Override
	public int getMaxStackSize(ItemStack stack) {
		return 1;
	}
}

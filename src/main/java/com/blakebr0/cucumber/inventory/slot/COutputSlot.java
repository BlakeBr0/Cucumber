package com.blakebr0.cucumber.inventory.slot;

import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.transfer.item.ItemStacksResourceHandler;
import net.neoforged.neoforge.transfer.item.ResourceHandlerSlot;

public class COutputSlot extends ResourceHandlerSlot {
	public COutputSlot(ItemStacksResourceHandler inventory, int index, int xPosition, int yPosition) {
		super(inventory, inventory::set, index, xPosition, yPosition);
	}

	@Override
	public boolean mayPlace(ItemStack stack) {
		return false;
	}
}

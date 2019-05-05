package com.blakebr0.cucumber.inventory.slot;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class SlotSingle extends Slot {
	public SlotSingle(IInventory inventory, int index, int xPosition, int yPosition) {
		super(inventory, index, xPosition, yPosition);
	}

	@Override
	public int getSlotStackLimit() {
		return 1;
	}
}

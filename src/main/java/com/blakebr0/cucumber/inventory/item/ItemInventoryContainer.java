package com.blakebr0.cucumber.inventory.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.ItemStack;

/*
 * Based on CoFHCore's inventory item container 
 * https://github.com/CoFH/CoFHCore/blob/1.12/src/main/java/cofh/core/gui/container/ContainerInventoryItem.java
 */
// TODO: 1.16: reevaluate
public class ItemInventoryContainer extends Container {
	protected final ItemInventoryWrapper wrapper;
	protected final PlayerInventory inventory;
	protected final int index;
	protected boolean valid = true;

	public ItemInventoryContainer(ContainerType<?> type, ItemStack inv, int size, PlayerInventory inventory) {
		super(type, 0);
		this.inventory = inventory;
		this.index = inventory.selected;
		this.wrapper = new ItemInventoryWrapper(inv, size);
	}
	
	@Override
	public void broadcastChanges() {
		ItemStack stack = this.inventory.items.get(this.index);
		if (stack.isEmpty() || stack.getItem() != this.wrapper.getInventory().getItem()) {
			this.valid = false;
			return;
		}

		super.broadcastChanges();
	}

	@Override
	public boolean stillValid(PlayerEntity player) {
		this.onSlotChanged();
		if (this.wrapper.getDirty() && !this.valid) {
			player.inventory.setCarried(ItemStack.EMPTY);
		}

		return this.valid;
	}

	public void onSlotChanged() {
		ItemStack stack = this.inventory.items.get(this.index);
		if (this.valid && !stack.isEmpty() && stack.getItem() == this.wrapper.getInventory().getItem()) {
			this.inventory.items.set(this.index, this.wrapper.getInventory());
		}
	}
}

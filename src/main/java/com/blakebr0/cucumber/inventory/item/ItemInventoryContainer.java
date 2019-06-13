package com.blakebr0.cucumber.inventory.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;

/*
 * Based on CoFHCore's inventory item container 
 * https://github.com/CoFH/CoFHCore/blob/1.12/src/main/java/cofh/core/gui/container/ContainerInventoryItem.java
 */
public class ItemInventoryContainer extends Container {
	protected final ItemInventoryWrapper wrapper;
	protected final PlayerInventory inventory;
	protected final int index;
	protected boolean valid = true;

	public ItemInventoryContainer(ItemStack inv, int size, PlayerInventory inventory) {
		super(null, 0);
		this.inventory = inventory;
		this.index = inventory.currentItem;
		this.wrapper = new ItemInventoryWrapper(inv, size);
	}
	
	@Override
	public void detectAndSendChanges() {
		ItemStack item = this.inventory.mainInventory.get(this.index);
		if (item.isEmpty() || item.getItem() != this.wrapper.getInventory().getItem()) {
			this.valid = false;
			return;
		}

		super.detectAndSendChanges();
	}

	public void onSlotChanged() {
		ItemStack item = this.inventory.mainInventory.get(this.index);
		if (this.valid && !item.isEmpty() && item.getItem() == this.wrapper.getInventory().getItem()) {
			this.inventory.mainInventory.set(this.index, this.wrapper.getInventory());
		}
	}

	@Override
	public boolean canInteractWith(PlayerEntity player) {
		onSlotChanged();
		if (this.wrapper.getDirty() && !this.valid) {
			player.inventory.setItemStack(ItemStack.EMPTY);
		}

		return this.valid;
	}
}

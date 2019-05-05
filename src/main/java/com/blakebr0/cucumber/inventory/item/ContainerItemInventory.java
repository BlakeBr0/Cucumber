package com.blakebr0.cucumber.inventory.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;

/*
 * Based on CoFHCore's inventory item container 
 * https://github.com/CoFH/CoFHCore/blob/1.12/src/main/java/cofh/core/gui/container/ContainerInventoryItem.java
 */
public class ContainerItemInventory extends Container {
	protected final ItemInventoryWrapper inventory;
	protected final EntityPlayer player;
	protected final int index;
	protected boolean valid = true;

	public ContainerItemInventory(ItemStack inv, int size, InventoryPlayer inventory) {
		this.player = inventory.player;
		this.index = inventory.currentItem;
		this.inventory = new ItemInventoryWrapper(inv, size);
	}
	
	@Override
	public void detectAndSendChanges() {
		ItemStack item = this.player.inventory.mainInventory.get(this.index);
		if (item.isEmpty() || item.getItem() != this.inventory.getInventory().getItem()) {
			this.valid = false;
			return;
		}

		super.detectAndSendChanges();
	}

	public void onSlotChanged() {
		ItemStack item = this.player.inventory.mainInventory.get(this.index);
		if (this.valid && !item.isEmpty() && item.getItem() == this.inventory.getInventory().getItem()) {
			this.player.inventory.mainInventory.set(this.index, this.inventory.getInventory());
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		onSlotChanged();
		if (this.inventory.getDirty() && !this.valid) {
			player.inventory.setItemStack(ItemStack.EMPTY);
		}

		return this.valid;
	}
}

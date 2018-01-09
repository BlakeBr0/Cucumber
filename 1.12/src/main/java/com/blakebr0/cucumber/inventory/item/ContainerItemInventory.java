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
		ItemStack item = player.inventory.mainInventory.get(index);
		if (item.isEmpty() || item.getItem() != inventory.inventory.getItem()) {
			valid = false;
			return;
		}
		super.detectAndSendChanges();
	}

	public void onSlotChanged() {
		ItemStack item = player.inventory.mainInventory.get(index);
		if (valid && !item.isEmpty() && item.getItem() == inventory.inventory.getItem()) {
			player.inventory.mainInventory.set(index, inventory.inventory);
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		onSlotChanged();
		if (inventory.getDirty() && !valid) {
			player.inventory.setItemStack(ItemStack.EMPTY);
		}
		return valid;
	}
}

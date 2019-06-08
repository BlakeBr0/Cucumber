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
	protected final ItemInventoryWrapper inventory;
	protected final PlayerEntity player;
	protected final int index;
	protected boolean valid = true;

	public ItemInventoryContainer(ItemStack inv, int size, PlayerInventory inventory) {
		super(null, 0);
		this.player = inventory.field_70458_d;
		this.index = inventory.currentItem;
		this.inventory = new ItemInventoryWrapper(inv, size);
	}
	
	@Override
	public void detectAndSendChanges() {
		ItemStack item = this.player.field_71071_by.mainInventory.get(this.index);
		if (item.isEmpty() || item.getItem() != this.inventory.getInventory().getItem()) {
			this.valid = false;
			return;
		}

		super.detectAndSendChanges();
	}

	public void onSlotChanged() {
		ItemStack item = this.player.field_71071_by.mainInventory.get(this.index);
		if (this.valid && !item.isEmpty() && item.getItem() == this.inventory.getInventory().getItem()) {
			this.player.field_71071_by.mainInventory.set(this.index, this.inventory.getInventory());
		}
	}

	@Override
	public boolean canInteractWith(PlayerEntity player) {
		onSlotChanged();
		if (this.inventory.getDirty() && !this.valid) {
			player.field_71071_by.setItemStack(ItemStack.EMPTY);
		}

		return this.valid;
	}
}

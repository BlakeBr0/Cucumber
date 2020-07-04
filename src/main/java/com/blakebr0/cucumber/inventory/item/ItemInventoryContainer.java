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
public class ItemInventoryContainer extends Container {
	protected final ItemInventoryWrapper wrapper;
	protected final PlayerInventory inventory;
	protected final int index;
	protected boolean valid = true;

	public ItemInventoryContainer(ContainerType<?> type, ItemStack inv, int size, PlayerInventory inventory) {
		super(type, 0);
		this.inventory = inventory;
		this.index = inventory.currentItem;
		this.wrapper = new ItemInventoryWrapper(inv, size);
	}
	
	@Override
	public void detectAndSendChanges() {
		ItemStack stack = this.inventory.mainInventory.get(this.index);
		if (stack.isEmpty() || stack.getItem() != this.wrapper.getInventory().getItem()) {
			this.valid = false;
			return;
		}

		super.detectAndSendChanges();
	}

	@Override
	public boolean canInteractWith(PlayerEntity player) {
		this.onSlotChanged();
		if (this.wrapper.getDirty() && !this.valid) {
			player.inventory.setItemStack(ItemStack.EMPTY);
		}

		return this.valid;
	}

	public void onSlotChanged() {
		ItemStack stack = this.inventory.mainInventory.get(this.index);
		if (this.valid && !stack.isEmpty() && stack.getItem() == this.wrapper.getInventory().getItem()) {
			this.inventory.mainInventory.set(this.index, this.wrapper.getInventory());
		}
	}
}

package com.blakebr0.cucumber.inventory.item;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;

/*
 * Based on CoFHCore's inventory item container 
 * https://github.com/CoFH/CoFHCore/blob/1.12/src/main/java/cofh/core/gui/container/ContainerInventoryItem.java
 */
public class ItemInventoryContainer extends AbstractContainerMenu {
	protected final ItemInventoryWrapper wrapper;
	protected final Inventory inventory;
	protected final int index;
	protected boolean valid = true;

	public ItemInventoryContainer(MenuType<?> type, ItemStack inv, int size, Inventory inventory) {
		super(type, 0);
		this.inventory = inventory;
		this.index = inventory.selected;
		this.wrapper = new ItemInventoryWrapper(inv, size);
	}
	
	@Override
	public void broadcastChanges() {
		var stack = this.inventory.items.get(this.index);

		if (stack.isEmpty() || stack.getItem() != this.wrapper.getInventory().getItem()) {
			this.valid = false;
			return;
		}

		super.broadcastChanges();
	}

	@Override
	public ItemStack quickMoveStack(Player p_38941_, int p_38942_) {
		return ItemStack.EMPTY;
	}

	@Override
	public boolean stillValid(Player player) {
		this.onSlotChanged();

		if (this.wrapper.getDirty() && !this.valid) {
			player.getInventory().setPickedItem(ItemStack.EMPTY);
		}

		return this.valid;
	}

	public void onSlotChanged() {
		var stack = this.inventory.items.get(this.index);

		if (this.valid && !stack.isEmpty() && stack.getItem() == this.wrapper.getInventory().getItem()) {
			this.inventory.items.set(this.index, this.wrapper.getInventory());
		}
	}
}

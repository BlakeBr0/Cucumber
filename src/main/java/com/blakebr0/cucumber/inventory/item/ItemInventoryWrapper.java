package com.blakebr0.cucumber.inventory.item;

import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class ItemInventoryWrapper implements Container {
	private final ItemStack inventory;
	private final int size;
	private final NonNullList<ItemStack> slots;
	private CompoundTag tag;
	private boolean dirty = false;
	
	public ItemInventoryWrapper(ItemStack inventory, int size) {
		this.inventory = inventory;
		this.size = size;
		this.slots = NonNullList.withSize(size, ItemStack.EMPTY);
		
		load();
		setChanged();
	}
	
	public void load() {
		var nbt = this.inventory.getTag();

		if (nbt != null) {
			if (!nbt.contains("Items")) {
				if (this.inventory.hasTag()) {
					this.tag = nbt;
					loadItems();
					this.tag = new CompoundTag();
					saveItems();
				} else {
					this.inventory.addTagElement("Inventory", new CompoundTag());
				}

				this.tag = nbt.getCompound("Inventory");

				loadItems();
			}
		}
	}
	
	protected void loadItems() {
		for (var i = 0; i < this.size; i++) {
			if (this.tag.contains("Slot")) {
				this.slots.set(i, ItemStack.of(tag.getCompound("Slot" + i)));
			} else {
				this.slots.set(i, ItemStack.EMPTY);
			}
		}
	}
	
	protected void saveItems() {
		for (var i = 0; i < this.size; i++) {
			if (this.slots.get(i).isEmpty()) {
				this.tag.remove("Slot" + i);
			} else{ 
				this.tag.put("Slot" + i, this.slots.get(i).save(new CompoundTag()));
			}
		}

		this.inventory.addTagElement("Items", this.tag);
	}

	public ItemStack getInventory() {
		return this.inventory;
	}

	public NonNullList<ItemStack> getStacks() {
		return this.slots;
	}
	
	public boolean getDirty() {
		var dirt = dirty;

		this.dirty = false;

		return dirt;
	}

	@Override
	public int getContainerSize() {
		return this.size;
	}

	@Override
	public boolean isEmpty() {
		return this.slots.stream().allMatch(ItemStack::isEmpty);
	}

	@Override
	public ItemStack getItem(int index) {
		return this.slots.get(index);
	}

	@Override
	public ItemStack removeItem(int index, int count) {
		return ContainerHelper.removeItem(this.slots, index, count);
	}

	@Override
	public ItemStack removeItemNoUpdate(int index) {
		return ContainerHelper.takeItem(this.slots, index);
	}

	@Override
	public void setItem(int index, ItemStack stack) {
		this.slots.set(index, stack);
	}

	@Override
	public int getMaxStackSize() {
		return 64;
	}

	@Override
	public void setChanged() {
		saveItems();
		this.dirty = true;
	}

	@Override
	public boolean stillValid(Player player) {
		return true;
	}

	@Override
	public void startOpen(Player player) { }

	@Override
	public void stopOpen(Player player) {
		setChanged();
	}

	@Override
	public boolean canPlaceItem(int index, ItemStack stack) {
		return true;
	}

	@Override
	public void clearContent() { }
}

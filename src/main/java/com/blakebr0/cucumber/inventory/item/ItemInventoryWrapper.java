package com.blakebr0.cucumber.inventory.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.NonNullList;

// TODO: 1.16: reevaluate
public class ItemInventoryWrapper implements IInventory {
	private final ItemStack inventory;
	private final int size;
	private final NonNullList<ItemStack> slots;
	private CompoundNBT tag;
	private boolean dirty = false;
	
	public ItemInventoryWrapper(ItemStack inventory, int size) {
		this.inventory = inventory;
		this.size = size;
		this.slots = NonNullList.withSize(size, ItemStack.EMPTY);
		
		load();
		setChanged();
	}
	
	public void load() {
		CompoundNBT nbt = this.inventory.getTag();
		if (!this.inventory.hasTag() || !nbt.contains("Items")) {
			if (this.inventory.hasTag()) {
				this.tag = nbt;
				loadItems();
				this.tag = new CompoundNBT();
				saveItems();
			} else {
				this.inventory.addTagElement("Inventory", new CompoundNBT());
			}
		}

		this.tag = nbt.getCompound("Inventory");
		loadItems();
	}
	
	protected void loadItems() {
		for (int i = 0; i < this.size; i++) {
			if (this.tag.contains("Slot")) {
				this.slots.set(i, ItemStack.of(tag.getCompound("Slot" + i)));
			} else {
				this.slots.set(i, ItemStack.EMPTY);
			}
		}
	}
	
	protected void saveItems() {
		for (int i = 0; i < this.size; i++) {
			if (this.slots.get(i).isEmpty()) {
				this.tag.remove("Slot" + i);
			} else{ 
				this.tag.put("Slot" + i, this.slots.get(i).save(new CompoundNBT()));
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
		boolean dirt = dirty;
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
		return ItemStackHelper.removeItem(this.slots, index, count);
	}

	@Override
	public ItemStack removeItemNoUpdate(int index) {
		return ItemStackHelper.takeItem(this.slots, index);
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
	public boolean stillValid(PlayerEntity player) {
		return true;
	}

	@Override
	public void startOpen(PlayerEntity player) { }

	@Override
	public void stopOpen(PlayerEntity player) {
		setChanged();
	}

	@Override
	public boolean canPlaceItem(int index, ItemStack stack) {
		return true;
	}

	@Override
	public void clearContent() { }
}

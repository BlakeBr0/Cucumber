package com.blakebr0.cucumber.inventory.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;

public class ItemInventoryWrapper implements IInventory {

	private ItemStack inventory;
	private int size;
	private NonNullList<ItemStack> slots;
	private NBTTagCompound tag;
	private boolean dirty = false;
	
	public ItemInventoryWrapper(ItemStack inventory, int size) {
		this.inventory = inventory;
		this.size = size;
		this.slots = NonNullList.withSize(size, ItemStack.EMPTY);
		
		load();
		markDirty();
	}
	
	public void load() {
		NBTTagCompound nbt = this.inventory.getTag();
		if (!this.inventory.hasTag() || !nbt.contains("Items")) {
			if (this.inventory.hasTag()) {
				this.tag = nbt;
				loadItems();
				this.tag = new NBTTagCompound();
				saveItems();
			} else {
				this.inventory.setTagInfo("Inventory", new NBTTagCompound());
			}
		}

		this.tag = nbt.getCompound("Inventory");
		loadItems();
	}
	
	protected void loadItems() {
		for (int i = 0; i < this.size; i++) {
			if (this.tag.contains("Slot")) {
				this.slots.set(i, ItemStack.read(tag.getCompound("Slot" + i)));
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
				this.tag.put("Slot" + i, this.slots.get(i).write(new NBTTagCompound()));
			}
		}

		this.inventory.setTagInfo("Items", this.tag);
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
	public ITextComponent getName() {
		return this.inventory.getDisplayName();
	}

	@Override
	public boolean hasCustomName() {
		return this.inventory.hasDisplayName();
	}

	@Override
	public ITextComponent getDisplayName() {
		return this.inventory.getDisplayName();
	}

	@Override
	public int getSizeInventory() {
		return this.size;
	}

	@Override
	public boolean isEmpty() {
		return !this.slots.stream().anyMatch(s -> !s.isEmpty());
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		return this.slots.get(index);
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		return ItemStackHelper.getAndSplit(this.slots, index, count);
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		return ItemStackHelper.getAndRemove(this.slots, index);
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		this.slots.set(index, stack);
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public void markDirty() {
		saveItems();
		this.dirty = true;
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player) {
		return true;
	}

	@Override
	public void openInventory(EntityPlayer player) {
		
	}

	@Override
	public void closeInventory(EntityPlayer player) {
		markDirty();
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return true;
	}

	@Override
	public int getField(int id) {
		return 0;
	}

	@Override
	public void setField(int id, int value) {
		
	}

	@Override
	public int getFieldCount() {
		return 0;
	}

	@Override
	public void clear() {
		
	}

	@Override
	public ITextComponent getCustomName() {
		return null;
	}
}

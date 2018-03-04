package com.blakebr0.cucumber.inventory.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

public class ItemInventoryWrapper implements IInventory {
	
	public ItemStack inventory;
	public int size;
	
	public NonNullList<ItemStack> slots;
	protected NBTTagCompound tag;
	public boolean dirty = false;
	
	public ItemInventoryWrapper(ItemStack inventory, int size) {
		this.inventory = inventory;
		this.size = size;
		this.slots = NonNullList.withSize(size, ItemStack.EMPTY);
		
		load();
		markDirty();
	}
	
	public void load() {
		NBTTagCompound nbt = inventory.getTagCompound();
		if (!inventory.hasTagCompound() || !nbt.hasKey("Items")) {
			if (inventory.hasTagCompound()) {
				tag = nbt;
				loadItems();
				tag = new NBTTagCompound();
				saveItems();
			} else {
				inventory.setTagInfo("Inventory", new NBTTagCompound());
			}
		}
		tag = nbt.getCompoundTag("Inventory");
		loadItems();
	}
	
	protected void loadItems() {
		for (int i = 0; i < size; i++) {
			if (tag.hasKey("Slot")) {
				slots.set(i, new ItemStack(tag.getCompoundTag("Slot" + i)));
			} else {
				slots.set(i, ItemStack.EMPTY);
			}
		}
	}
	
	protected void saveItems() {
		for (int i = 0; i < size; i++) {
			if (slots.get(i).isEmpty()) {
				tag.removeTag("Slot" + i);
			} else{ 
				tag.setTag("Slot" + i, slots.get(i).writeToNBT(new NBTTagCompound()));
			}
		}
		inventory.setTagInfo("Items", tag);
	}
	
	public boolean getDirty() {
		boolean dirt = dirty;
		dirty = false;
		return dirt;
	}

	@Override
	public String getName() {
		return inventory.getDisplayName();
	}

	@Override
	public boolean hasCustomName() {
		return inventory.hasDisplayName();
	}

	@Override
	public ITextComponent getDisplayName() {
		return new TextComponentString(inventory.getDisplayName());
	}

	@Override
	public int getSizeInventory() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		for (ItemStack s : slots) {
			if (!s.isEmpty()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		return slots.get(index);
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		return ItemStackHelper.getAndSplit(slots, index, count);
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		return ItemStackHelper.getAndRemove(slots, index);
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		slots.set(index, stack);
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public void markDirty() {
		saveItems();
		dirty = true;
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
}

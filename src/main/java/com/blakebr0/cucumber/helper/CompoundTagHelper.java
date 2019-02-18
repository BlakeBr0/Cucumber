package com.blakebr0.cucumber.helper;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.INBTBase;
import net.minecraft.nbt.NBTTagCompound;

public class CompoundTagHelper {
	
	String name;
	
	public CompoundTagHelper(String name) {
		this.name = name;
	}
	
	public NBTTagCompound getCompoundTag(ItemStack stack) {
		return stack.getOrCreateChildTag(this.name);
	}
	
	public void setTag(ItemStack stack, String key, INBTBase value) {
		getCompoundTag(stack).setTag(key, value);
	}
	
	public void setByte(ItemStack stack, String key, byte value) {
		getCompoundTag(stack).setByte(key, value);
	}
	
	public void setShort(ItemStack stack, String key, short value) {
		getCompoundTag(stack).setShort(key, value);
	}

	public void setInt(ItemStack stack, String key, int value) {
		getCompoundTag(stack).setInt(key, value);
	}
	
	public void setLong(ItemStack stack, String key, long value) {
		getCompoundTag(stack).setLong(key, value);
	}
	
	public void setFloat(ItemStack stack, String key, float value) {
		getCompoundTag(stack).setFloat(key, value);
	}
	
	public void setDouble(ItemStack stack, String key, double value) {
		getCompoundTag(stack).setDouble(key, value);
	}
	
	public void setString(ItemStack stack, String key, String value) {
		getCompoundTag(stack).setString(key, value);
	}
	
	public void setByteArray(ItemStack stack, String key, byte[] value) {
		getCompoundTag(stack).setByteArray(key, value);
	}
	
	public void setIntArray(ItemStack stack, String key, int[] value) {
		getCompoundTag(stack).setIntArray(key, value);
	}
	
	public void setBoolean(ItemStack stack, String key, boolean value) {
		getCompoundTag(stack).setBoolean(key, value);
	}
	
	public INBTBase getTag(ItemStack stack, String key) {
		return getCompoundTag(stack).getTag(key);
	}
	
	public byte getByte(ItemStack stack, String key) {
		return getCompoundTag(stack).getByte(key);
	}
	
	public short getShort(ItemStack stack, String key) {
		return getCompoundTag(stack).getShort(key);
	}
	
	public int getInt(ItemStack stack, String key) {
		return getCompoundTag(stack).getInt(key);
	}
	
	public long getLong(ItemStack stack, String key) {
		return getCompoundTag(stack).getLong(key);
	}
	
	public float getFloat(ItemStack stack, String key) {
		return getCompoundTag(stack).getFloat(key);
	}
	
	public double getDouble(ItemStack stack, String key) {
		return getCompoundTag(stack).getDouble(key);
	}
	
	public String getString(ItemStack stack, String key) {
		return getCompoundTag(stack).getString(key);
	}
	
	public byte[] getByteArray(ItemStack stack, String key) {
		return getCompoundTag(stack).getByteArray(key);
	}
	
	public int[] getIntArray(ItemStack stack, String key) {
		return getCompoundTag(stack).getIntArray(key);
	}
	
	public boolean getBoolean(ItemStack stack, String key) {
		return getCompoundTag(stack).getBoolean(key);
	}
	
	public boolean hasKey(ItemStack stack, String key) {
		return getCompoundTag(stack).hasKey(key);
	}
	
	public void flipBoolean(ItemStack stack, String key) {
		setBoolean(stack, key, !getBoolean(stack, key));
	}
	
	public void removeTag(ItemStack stack, String key) {
		if (hasKey(stack, key)) {
			getCompoundTag(stack).removeTag(key);
		}
	}
}

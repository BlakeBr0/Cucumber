package com.blakebr0.cucumber.helper;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;

// TODO: 1.16: reevaluate
public class CompoundTagHelper {
	private String name;
	
	public CompoundTagHelper(String name) {
		this.name = name;
	}
	
	public CompoundNBT getCompoundTag(ItemStack stack) {
		return stack.getOrCreateChildTag(this.name);
	}
	
	public void setTag(ItemStack stack, String key, INBT value) {
		getCompoundTag(stack).put(key, value);
	}
	
	public void setByte(ItemStack stack, String key, byte value) {
		getCompoundTag(stack).putByte(key, value);
	}
	
	public void setShort(ItemStack stack, String key, short value) {
		getCompoundTag(stack).putShort(key, value);
	}

	public void setInt(ItemStack stack, String key, int value) {
		getCompoundTag(stack).putInt(key, value);
	}
	
	public void setLong(ItemStack stack, String key, long value) {
		getCompoundTag(stack).putLong(key, value);
	}
	
	public void setFloat(ItemStack stack, String key, float value) {
		getCompoundTag(stack).putFloat(key, value);
	}
	
	public void setDouble(ItemStack stack, String key, double value) {
		getCompoundTag(stack).putDouble(key, value);
	}
	
	public void setString(ItemStack stack, String key, String value) {
		getCompoundTag(stack).putString(key, value);
	}
	
	public void setByteArray(ItemStack stack, String key, byte[] value) {
		getCompoundTag(stack).putByteArray(key, value);
	}
	
	public void setIntArray(ItemStack stack, String key, int[] value) {
		getCompoundTag(stack).putIntArray(key, value);
	}
	
	public void setBoolean(ItemStack stack, String key, boolean value) {
		getCompoundTag(stack).putBoolean(key, value);
	}
	
	public INBT getTag(ItemStack stack, String key) {
		return getCompoundTag(stack).get(key);
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
		return getCompoundTag(stack).contains(key);
	}
	
	public void flipBoolean(ItemStack stack, String key) {
		setBoolean(stack, key, !getBoolean(stack, key));
	}
	
	public void removeTag(ItemStack stack, String key) {
		if (hasKey(stack, key)) {
			getCompoundTag(stack).remove(key);
		}
	}
}

package com.blakebr0.cucumber.helper;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.INBTBase;
import net.minecraft.nbt.NBTTagCompound;

public class NBTHelper {
	
	public static void setTag(ItemStack stack, String key, INBTBase value) {
		validateCompound(stack);
		
		stack.getTag().put(key, value);
	}
	
	public static void setByte(ItemStack stack, String key, byte value) {
		validateCompound(stack);
		
		stack.getTag().putByte(key, value);
	}
	
	public static void setShort(ItemStack stack, String key, short value) {
		validateCompound(stack);
		
		stack.getTag().putShort(key, value);
	}

	public static void setInt(ItemStack stack, String key, int value) {
		validateCompound(stack);
		
		stack.getTag().putInt(key, value);
	}
	
	public static void setLong(ItemStack stack, String key, long value) {
		validateCompound(stack);
		
		stack.getTag().putLong(key, value);
	}
	
	public static void setFloat(ItemStack stack, String key, float value) {
		validateCompound(stack);
		
		stack.getTag().putFloat(key, value);
	}
	
	public static void setDouble(ItemStack stack, String key, double value) {
		validateCompound(stack);
		
		stack.getTag().putDouble(key, value);
	}
	
	public static void setString(ItemStack stack, String key, String value) {
		validateCompound(stack);
		
		stack.getTag().putString(key, value);
	}
	
	public static void setByteArray(ItemStack stack, String key, byte[] value) {
		validateCompound(stack);
		
		stack.getTag().putByteArray(key, value);
	}
	
	public static void setIntArray(ItemStack stack, String key, int[] value) {
		validateCompound(stack);
		
		stack.getTag().putIntArray(key, value);
	}
	
	public static void setBoolean(ItemStack stack, String key, boolean value) {
		validateCompound(stack);
		
		stack.getTag().putBoolean(key, value);
	}
	
	public static INBTBase getTag(ItemStack stack, String key) {
		return stack.hasTag() ? stack.getTag().get(key) : null;
	}
	
	public static byte getByte(ItemStack stack, String key) {
		return stack.hasTag() ? stack.getTag().getByte(key) : 0;
	}
	
	public static short getShort(ItemStack stack, String key) {
		return stack.hasTag() ? stack.getTag().getShort(key) : 0;
	}
	
	public static int getInt(ItemStack stack, String key) {
		return stack.hasTag() ? stack.getTag().getInt(key) : 0;
	}
	
	public static long getLong(ItemStack stack, String key) {
		return stack.hasTag() ? stack.getTag().getLong(key) : 0L;
	}
	
	public static float getFloat(ItemStack stack, String key) {
		return stack.hasTag() ? stack.getTag().getFloat(key) : 0.0F;
	}
	
	public static double getDouble(ItemStack stack, String key) {
		return stack.hasTag() ? stack.getTag().getDouble(key) : 0.0D;
	}
	
	public static String getString(ItemStack stack, String key) {
		return stack.hasTag() ? stack.getTag().getString(key) : "";
	}
	
	public static byte[] getByteArray(ItemStack stack, String key) {
		return stack.hasTag() ? stack.getTag().getByteArray(key) : new byte[0];
	}
	
	public static int[] getIntArray(ItemStack stack, String key) {
		return stack.hasTag() ? stack.getTag().getIntArray(key) : new int[0];
	}
	
	public static boolean getBoolean(ItemStack stack, String key) {
		return stack.hasTag() ? stack.getTag().getBoolean(key) : false;
	}
	
	public static boolean hasKey(ItemStack stack, String key) {
		return stack.hasTag() && stack.getTag().contains(key);
	}
	
	public static void flipBoolean(ItemStack stack, String key) {
		validateCompound(stack);
		
		setBoolean(stack, key, !getBoolean(stack, key));
	}
	
	public static void removeTag(ItemStack stack, String key) {
		if (hasKey(stack, key)) {
			stack.getTag().remove(key);
		}
	}
	
	public static void validateCompound(ItemStack stack) {
		if (!stack.hasTag()) {
			NBTTagCompound tag = new NBTTagCompound();
			stack.setTag(tag);
		}
	}
	
	public static NBTTagCompound getTagCompound(ItemStack stack) {
		validateCompound(stack);
		return stack.getTag();
	}
	
	public static CompoundTagHelper newCompoundTagHelper(String name) {
		return new CompoundTagHelper(name);
	}
}

package com.blakebr0.cucumber.helper;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;

public class NBTHelper {
	
	public static void setTag(ItemStack stack, String key, NBTBase value) {
		validateCompound(stack);
		
		stack.getTagCompound().setTag(key, value);
	}
	
	public static void setByte(ItemStack stack, String key, byte value) {
		validateCompound(stack);
		
		stack.getTagCompound().setByte(key, value);
	}
	
	public static void setShort(ItemStack stack, String key, short value) {
		validateCompound(stack);
		
		stack.getTagCompound().setShort(key, value);
	}

	public static void setInt(ItemStack stack, String key, int value) {
		validateCompound(stack);
		
		stack.getTagCompound().setInteger(key, value);
	}
	
	public static void setLong(ItemStack stack, String key, long value) {
		validateCompound(stack);
		
		stack.getTagCompound().setLong(key, value);
	}
	
	public static void setFloat(ItemStack stack, String key, float value) {
		validateCompound(stack);
		
		stack.getTagCompound().setFloat(key, value);
	}
	
	public static void setDouble(ItemStack stack, String key, double value) {
		validateCompound(stack);
		
		stack.getTagCompound().setDouble(key, value);
	}
	
	public static void setString(ItemStack stack, String key, String value) {
		validateCompound(stack);
		
		stack.getTagCompound().setString(key, value);
	}
	
	public static void setByteArray(ItemStack stack, String key, byte[] value) {
		validateCompound(stack);
		
		stack.getTagCompound().setByteArray(key, value);
	}
	
	public static void setIntArray(ItemStack stack, String key, int[] value) {
		validateCompound(stack);
		
		stack.getTagCompound().setIntArray(key, value);
	}
	
	public static void setBoolean(ItemStack stack, String key, boolean value) {
		validateCompound(stack);
		
		stack.getTagCompound().setBoolean(key, value);
	}
	
	public static NBTBase getTag(ItemStack stack, String key) {
		return stack.hasTagCompound() ? stack.getTagCompound().getTag(key) : null;
	}
	
	public static byte getByte(ItemStack stack, String key) {
		return stack.hasTagCompound() ? stack.getTagCompound().getByte(key) : 0;
	}
	
	public static short getShort(ItemStack stack, String key) {
		return stack.hasTagCompound() ? stack.getTagCompound().getShort(key) : 0;
	}
	
	public static int getInt(ItemStack stack, String key) {
		return stack.hasTagCompound() ? stack.getTagCompound().getInteger(key) : 0;
	}
	
	public static long getLong(ItemStack stack, String key) {
		return stack.hasTagCompound() ? stack.getTagCompound().getLong(key) : 0L;
	}
	
	public static float getFloat(ItemStack stack, String key) {
		return stack.hasTagCompound() ? stack.getTagCompound().getFloat(key) : 0.0F;
	}
	
	public static double getDouble(ItemStack stack, String key) {
		return stack.hasTagCompound() ? stack.getTagCompound().getDouble(key) : 0.0D;
	}
	
	public static String getString(ItemStack stack, String key) {
		return stack.hasTagCompound() ? stack.getTagCompound().getString(key) : "";
	}
	
	public static byte[] getByteArray(ItemStack stack, String key) {
		return stack.hasTagCompound() ? stack.getTagCompound().getByteArray(key) : new byte[0];
	}
	
	public static int[] getIntArray(ItemStack stack, String key) {
		return stack.hasTagCompound() ? stack.getTagCompound().getIntArray(key) : new int[0];
	}
	
	public static boolean getBoolean(ItemStack stack, String key) {
		return stack.hasTagCompound() ? stack.getTagCompound().getBoolean(key) : false;
	}
	
	public static boolean hasKey(ItemStack stack, String key) {
		return stack.hasTagCompound() && stack.getTagCompound().hasKey(key);
	}
	
	public static void flipBoolean(ItemStack stack, String key) {
		validateCompound(stack);
		
		setBoolean(stack, key, !getBoolean(stack, key));
	}
	
	public static void removeTag(ItemStack stack, String key) {
		if (hasKey(stack, key)) {
			stack.getTagCompound().removeTag(key);
		}
	}
	
	public static void validateCompound(ItemStack stack) {
		if (!stack.hasTagCompound()) {
			NBTTagCompound tag = new NBTTagCompound();
			stack.setTagCompound(tag);
		}
	}
	
	public static NBTTagCompound getTagCompound(ItemStack stack) {
		validateCompound(stack);
		return stack.getTagCompound();
	}
	
	public static CompoundTagHelper newCompoundTagHelper(String name) {
		return new CompoundTagHelper(name);
	}
}

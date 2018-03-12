package com.blakebr0.cucumber.helper;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
// TODO: fill this out
public class NBTHelper {

	public static void setInt(ItemStack stack, String key, int value) {
		setupCompound(stack);
		
		stack.getTagCompound().setInteger(key, value);
	}
	
	public static void setString(ItemStack stack, String key, String value) {
		setupCompound(stack);
		
		stack.getTagCompound().setString(key, value);
	}
	
	public static int getInt(ItemStack stack, String key) {
		return stack.hasTagCompound() ? stack.getTagCompound().getInteger(key) : 0;
	}
	
	public static String getString(ItemStack stack, String key) {
		return stack.hasTagCompound() ? stack.getTagCompound().getString(key) : "";
	}
	
	public static boolean hasKey(ItemStack stack, String key) {
		return stack.hasTagCompound() ? stack.getTagCompound().hasKey(key) : false;
	}
	
	public static void setupCompound(ItemStack stack) {
		if (!stack.hasTagCompound()) {
			NBTTagCompound tag = new NBTTagCompound();
			stack.setTagCompound(tag);
		}
	}
}

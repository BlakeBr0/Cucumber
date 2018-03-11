package com.blakebr0.cucumber.helper;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
// TODO: fill this out
public class NBTHelper {

	public static int getInt(ItemStack stack, String key) {
		return stack.hasTagCompound() ? stack.getTagCompound().getInteger(key) : 0;
	}
	
	public static String getString(ItemStack stack, String key) {
		return stack.hasTagCompound() ? stack.getTagCompound().getString(key) : "";
	}
}

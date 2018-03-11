package com.blakebr0.cucumber.guide;

import com.blakebr0.cucumber.helper.NBTHelper;

import net.minecraft.item.ItemStack;

public class GuideBookHelper {
	
	public static String getName(ItemStack stack) {
		return NBTHelper.getString(stack, "Name");
	}
	
	public static String getModName(ItemStack stack) {
		return NBTHelper.getString(stack, "ModName");
	}
	
	public static String getAuthor(ItemStack stack) {
		return NBTHelper.getString(stack, "Author");
	}

	public static int getEntryPage(ItemStack stack) {
		return NBTHelper.getInt(stack, "Page");
	}
}

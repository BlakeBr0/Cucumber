package com.blakebr0.cucumber.guide;

import com.blakebr0.cucumber.helper.NBTHelper;

import net.minecraft.item.ItemStack;

public class GuideBookHelper {
	
	public static int getTopicsPage(ItemStack stack) {
		return NBTHelper.getInt(stack, "TopicsPage");
	}
	
	public static int getEntryId(ItemStack stack) {
		return NBTHelper.hasKey(stack, "EntryId") ? NBTHelper.getInt(stack, "EntryId") : -1;
	}
	
	public static int getEntryPage(ItemStack stack) {
		return NBTHelper.getInt(stack, "EntryPage");
	}
	
	public static void setTopicsPage(ItemStack stack, int page) {
		NBTHelper.setInt(stack, "TopicsPage", page);
	}
	
	public static void setEntryId(ItemStack stack, int id) {
		NBTHelper.setInt(stack, "EntryId", id);
	}
	
	public static void setEntryPage(ItemStack stack, int page) {
		NBTHelper.setInt(stack, "EntryPage", page);
	}
}

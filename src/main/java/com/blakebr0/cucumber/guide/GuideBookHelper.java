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

	public static int getTopicsPage(ItemStack stack) {
		return NBTHelper.getInt(stack, "TopicsPage");
	}
	
	public static int getEntryId(ItemStack stack) {
		return NBTHelper.hasKey(stack, "EntryId") ? NBTHelper.getInt(stack, "EntryId") : -1;
	}
	
	public static int getEntryPage(ItemStack stack) {
		return NBTHelper.getInt(stack, "EntryPage");
	}
	
	public static void setName(ItemStack stack, String name) {
		NBTHelper.setString(stack, "Name", name);
	}
	
	public static void setModName(ItemStack stack, String name) {
		NBTHelper.setString(stack, "ModName", name);
	}
	
	public static void setAuthor(ItemStack stack, String name) {
		NBTHelper.setString(stack, "Author", name);
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

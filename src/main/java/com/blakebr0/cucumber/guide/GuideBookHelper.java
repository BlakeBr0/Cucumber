package com.blakebr0.cucumber.guide;

import net.minecraft.item.ItemStack;

public class GuideBookHelper {

	public static int getEntryPage(ItemStack stack) {
		return stack.getTagCompound().getInteger("Page");
	}
}

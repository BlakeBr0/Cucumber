package com.blakebr0.cucumber.item.color;

import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;

/**
 * A generic EnumDyeColor item color handler
 * 
 * @author BlakeBr0
 */
public class ItemDyeColorHandler implements IItemColor {

	@Override
	public int getColorFromItemstack(ItemStack stack, int tintIndex) {
		return EnumDyeColor.byMetadata(stack.getMetadata()).getColorValue();
	}
}

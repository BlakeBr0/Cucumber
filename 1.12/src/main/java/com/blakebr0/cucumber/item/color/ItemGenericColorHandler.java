package com.blakebr0.cucumber.item.color;

import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;

/**
 * A generic item color handler
 * @author BlakeBr0
 */
public class ItemGenericColorHandler implements IItemColor {

	public int color;
	
	public ItemGenericColorHandler(int color){
		this.color = color;
	}
	
	@Override
	public int getColorFromItemstack(ItemStack stack, int tintIndex) {
		return this.color;
	}
}

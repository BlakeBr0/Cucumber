package com.blakebr0.cucumber.crafting;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class ShapedOreOutputRecipe extends ShapedOreRecipe {

	public String ore;
	public int amount;
	
	public ShapedOreOutputRecipe(ResourceLocation group, String ore, int amount, Object... recipe) {
		super(group, ItemStack.EMPTY, recipe);
	}
	
	@Override
	public ItemStack getCraftingResult(InventoryCrafting var1) {
		return getRecipeOutput();
	}
	
	@Override
	public ItemStack getRecipeOutput() {
		ItemStack stack = ItemStack.EMPTY;
		if (OreDictionary.doesOreNameExist(ore)) {
			if (!OreDictionary.getOres(ore).isEmpty()) {
				stack = OreDictionary.getOres(ore).get(0).copy();
			}
		}
		if (!stack.isEmpty()) {
			stack.setCount(amount);
		}
		return stack;
	}
}

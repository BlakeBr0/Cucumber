package com.blakebr0.cucumber.crafting;

import com.blakebr0.cucumber.helper.RecipeHelper;
import com.blakebr0.cucumber.helper.StackHelper;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class ShapelessOreOutputRecipe extends ShapelessOreRecipe {

	public String ore;
	public int amount;
	private ItemStack output;
	
	public ShapelessOreOutputRecipe(String ore, int amount, Object[] recipe) {
		this(RecipeHelper.EMPTY_GROUP, ore, amount, recipe);
	}
	
	public ShapelessOreOutputRecipe(ResourceLocation group, String ore, int amount, Object[] recipe) {
		super(group, ItemStack.EMPTY, recipe);
		this.ore = ore;
		this.amount = amount;
		OreOutputRecipeValidator.ORE_RECIPES.add(this);
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inv) {
		return this.getRecipeOutput().copy();
	}
	
	@Override
	public ItemStack getRecipeOutput() {
		if (this.output.isEmpty()) {
			if (OreDictionary.doesOreNameExist(this.ore)) {
				this.output = StackHelper.fromOre(this.ore, this.amount);
			}
		}
		
		return this.output;
	}
}

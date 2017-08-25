package com.blakebr0.cucumber.helper;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class RecipeHelper {

	public static final ResourceLocation EMPTY_GROUP = new ResourceLocation("", "");

	public static void addShapedRecipe(ItemStack output, Object... input) {
		for (Object obj : input) {
			if (obj == null) {
				return;
			}
		}
		ForgeRegistries.RECIPES
				.register(new ShapedOreRecipe(EMPTY_GROUP, output, input).setRegistryName(getRecipeLocation(output)));
	}

	public static void addShapelessRecipe(ItemStack output, Object... input) {
		for (Object obj : input) {
			if (obj == null) {
				return;
			}
		}
		ForgeRegistries.RECIPES.register(
				new ShapelessOreRecipe(EMPTY_GROUP, output, input).setRegistryName(getRecipeLocation(output)));
	}

	public static ResourceLocation getRecipeLocation(ItemStack output) {
		String namespace = Loader.instance().activeModContainer().getModId();
		ResourceLocation baseLoc = new ResourceLocation(namespace,
				output.getItem().getRegistryName().getResourcePath());
		ResourceLocation recipeLoc = baseLoc;
		int index = 0;

		while (CraftingManager.REGISTRY.containsKey(recipeLoc)) {
			index++;
			recipeLoc = new ResourceLocation(namespace, baseLoc.getResourcePath() + "_" + index);
		}

		return recipeLoc;
	}
}

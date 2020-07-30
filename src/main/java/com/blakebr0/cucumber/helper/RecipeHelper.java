package com.blakebr0.cucumber.helper;

import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.HashMap;
import java.util.Map;

public final class RecipeHelper {
    private static RecipeManager recipeManager;

    @SubscribeEvent
    public void onAddReloadListeners(AddReloadListenerEvent event) {
        recipeManager = event.getDataPackRegistries().getRecipeManager();
    }

    public static RecipeManager getRecipeManager() {
        if (!recipeManager.recipes.getClass().equals(HashMap.class)) {
            recipeManager.recipes = new HashMap<>(recipeManager.recipes);
            recipeManager.recipes.replaceAll((t, v) -> new HashMap<>(recipeManager.recipes.get(t)));
        }

        return recipeManager;
    }

    public static Map<IRecipeType<?>, Map<ResourceLocation, IRecipe<?>>> getRecipes() {
        return recipeManager.recipes;
    }

    public static void addRecipe(IRecipe<?> recipe) {
        getRecipeManager().recipes.computeIfAbsent(recipe.getType(), t -> new HashMap<>()).put(recipe.getId(), recipe);
    }
}

package com.blakebr0.cucumber.helper;

import com.google.common.collect.ImmutableMap;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.client.event.RecipesUpdatedEvent;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.server.ServerLifecycleHooks;

import java.util.HashMap;
import java.util.Map;

public final class RecipeHelper {
    private static RecipeManager recipeManager;

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onAddReloadListeners(AddReloadListenerEvent event) {
        recipeManager = event.getServerResources().getRecipeManager();
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onRecipesUpdated(RecipesUpdatedEvent event) {
        recipeManager = event.getRecipeManager();
    }

    public static RecipeManager getRecipeManager() {
        return recipeManager;
    }

    public static Map<RecipeType<?>, Map<ResourceLocation, Recipe<?>>> getRecipes() {
        return getRecipeManager().recipes;
    }

    public static <C extends Container, T extends Recipe<C>> Map<ResourceLocation, T> getRecipes(RecipeType<T> type) {
        return getRecipeManager().byType(type);
    }

    @Deprecated(forRemoval = true)
    public static void addRecipe(Recipe<?> recipe) {
        if (recipeManager.recipes instanceof ImmutableMap) {
            recipeManager.recipes = new HashMap<>(recipeManager.recipes);
            recipeManager.recipes.replaceAll((t, v) -> new HashMap<>(recipeManager.recipes.get(t)));
        }

        if (recipeManager.byName instanceof ImmutableMap) {
            recipeManager.byName = new HashMap<>(recipeManager.byName);
        }

        getRecipeManager().recipes.computeIfAbsent(recipe.getType(), t -> new HashMap<>()).put(recipe.getId(), recipe);
        getRecipeManager().byName.put(recipe.getId(), recipe);
    }
}

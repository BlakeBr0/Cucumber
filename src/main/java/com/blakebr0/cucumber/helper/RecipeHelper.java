package com.blakebr0.cucumber.helper;

import com.blakebr0.cucumber.Cucumber;
import com.blakebr0.cucumber.event.RecipeManagerLoadingEvent;
import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableMap;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.client.event.RecipesUpdatedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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

    public static void fireRecipeManagerLoadedEvent(RecipeManager manager, Map<RecipeType<?>, ImmutableMap.Builder<ResourceLocation, Recipe<?>>> map, ImmutableMap.Builder<ResourceLocation, Recipe<?>> builder) {
        var stopwatch = Stopwatch.createStarted();
        var recipes = new ArrayList<Recipe<?>>();

        try {
            MinecraftForge.EVENT_BUS.post(new RecipeManagerLoadingEvent(manager, recipes));
        } catch (Exception e) {
            Cucumber.LOGGER.error("An error occurred while firing RecipeManagerLoadingEvent", e);
        }

        for (var recipe : recipes) {
            map.computeIfAbsent(recipe.getType(), t -> ImmutableMap.builder()).put(recipe.getId(), recipe);
            builder.put(recipe.getId(), recipe);
        }

        Cucumber.LOGGER.info("Registered {} recipes in {} ms", recipes.size(), stopwatch.stop().elapsed(TimeUnit.MILLISECONDS));
    }

    public static void fireRecipeManagerLoadedEventKubeJSEdition(RecipeManager manager, Map<ResourceLocation, Recipe<?>> recipesByName) {
        var stopwatch = Stopwatch.createStarted();
        var recipes = new ArrayList<Recipe<?>>();

        try {
            MinecraftForge.EVENT_BUS.post(new RecipeManagerLoadingEvent(manager, recipes));
        } catch (Exception e) {
            Cucumber.LOGGER.error("An error occurred while firing RecipeManagerLoadingEvent", e);
        }

        for (var recipe : recipes) {
            recipesByName.put(recipe.getId(), recipe);
        }

        Cucumber.LOGGER.info("Registered {} recipes in {} ms (KubeJS mode)", recipes.size(), stopwatch.stop().elapsed(TimeUnit.MILLISECONDS));
    }
}

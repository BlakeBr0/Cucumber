package com.blakebr0.cucumber.helper;

import com.blakebr0.cucumber.Cucumber;
import com.blakebr0.cucumber.event.RecipeManagerLoadedEvent;
import com.blakebr0.cucumber.event.RecipeManagerLoadingEvent;
import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.common.NeoForge;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

public final class RecipeHelper {
    @Nullable
    private static WeakReference<RecipeManager> recipeManager;

    public static RecipeManager getRecipeManager() throws IllegalStateException {
        if (recipeManager == null || recipeManager.get() == null) {
            throw new IllegalStateException("Recipe Manager is not available");
        }

        return recipeManager.get();
    }

    @ApiStatus.Internal
    public static void setRecipeManager(RecipeManager manager) {
        recipeManager = new WeakReference<>(manager);
    }

    public static <I extends RecipeInput, T extends Recipe<I>> Collection<RecipeHolder<T>> byType(RecipeType<T> type) {
        return byType(getRecipeManager(), type);
    }

    public static <I extends RecipeInput, T extends Recipe<I>> Collection<RecipeHolder<T>> byType(RecipeManager manager, RecipeType<T> type) {
        return manager.byType(type);
    }

    public static <I extends RecipeInput, T extends Recipe<I>> List<T> byTypeValues(RecipeType<T> type) {
        return byTypeValues(getRecipeManager(), type);
    }

    public static <I extends RecipeInput, T extends Recipe<I>> List<T> byTypeValues(RecipeManager manager, RecipeType<T> type) {
        return byType(manager, type).stream().map(RecipeHolder::value).toList();
    }

    public static Collection<RecipeHolder<?>> getAllRecipes() {
        return getRecipeManager().getRecipes();
    }

    @ApiStatus.Internal
    public static void fireRecipeManagerLoadingEvent(RecipeManager manager, ImmutableMultimap.Builder<RecipeType<?>, RecipeHolder<?>> map,
                                                     ImmutableMap.Builder<ResourceLocation, RecipeHolder<?>> builder) {
        var stopwatch = Stopwatch.createStarted();
        var recipes = new ArrayList<RecipeHolder<?>>();

        try {
            NeoForge.EVENT_BUS.post(new RecipeManagerLoadingEvent(manager, recipes));
        } catch (Exception e) {
            Cucumber.LOGGER.error("An error occurred while firing RecipeManagerLoadingEvent", e);
        }

        for (var recipe : recipes) {
            map.put(recipe.value().getType(), recipe);
            builder.put(recipe.id(), recipe);
        }

        Cucumber.LOGGER.info("Registered {} recipes in {} ms", recipes.size(), stopwatch.stop().elapsed(TimeUnit.MILLISECONDS));
    }

    @ApiStatus.Internal
    public static void fireRecipeManagerLoadedEvent(RecipeManager manager) {
        try {
            NeoForge.EVENT_BUS.post(new RecipeManagerLoadedEvent(manager));
        } catch (Exception e) {
            Cucumber.LOGGER.error("An error occurred while firing RecipeManagerLoadedEvent", e);
        }
    }
}

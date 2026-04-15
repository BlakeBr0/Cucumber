package com.blakebr0.cucumber.helper;

import com.blakebr0.cucumber.Cucumber;
import com.blakebr0.cucumber.event.RecipeManagerLoadedEvent;
import com.blakebr0.cucumber.event.RecipeManagerLoadingEvent;
import com.google.common.base.Stopwatch;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.neoforged.neoforge.common.NeoForge;
import org.jetbrains.annotations.ApiStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public final class RecipeHelper {
    @ApiStatus.Internal
    public static void fireRecipeManagerLoadingEvent(RecipeManager manager, List<RecipeHolder<?>> output) {
        var stopwatch = Stopwatch.createStarted();
        var recipes = new ArrayList<RecipeHolder<?>>();

        try {
            NeoForge.EVENT_BUS.post(new RecipeManagerLoadingEvent(manager, recipes));
        } catch (Exception e) {
            Cucumber.LOGGER.error("An error occurred while firing RecipeManagerLoadingEvent", e);
        }

        output.addAll(recipes);

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

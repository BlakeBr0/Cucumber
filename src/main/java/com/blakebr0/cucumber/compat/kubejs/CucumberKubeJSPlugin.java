package com.blakebr0.cucumber.compat.kubejs;

import com.blakebr0.cucumber.helper.RecipeHelper;
import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.latvian.mods.kubejs.recipe.RecipesEventJS;
import dev.latvian.mods.kubejs.recipe.schema.RegisterRecipeSchemasEvent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.Map;

public class CucumberKubeJSPlugin extends KubeJSPlugin {
    @Override
    public void registerRecipeSchemas(RegisterRecipeSchemasEvent event) {
        // TODO: Implement custom schemas for these

        event.namespace("cucumber")
            .shaped("shaped_no_mirror")
            .shaped("shaped_transfer_damage")
        ;

        //.shaped("shaped_tag")
        //.shapeless("shapeless_tag")
    }

    @Override
    public void injectRuntimeRecipes(RecipesEventJS event, RecipeManager manager, Map<ResourceLocation, Recipe<?>> recipesByName) {
        RecipeHelper.fireRecipeManagerLoadedEventKubeJSEdition(manager, recipesByName);
    }
}

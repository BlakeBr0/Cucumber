package com.blakebr0.cucumber.event;

import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraftforge.eventbus.api.Event;

import java.util.List;

public class RegisterRecipesEvent extends Event {
    private final RecipeManager manager;
    private final List<Recipe<?>> recipes;

    public RegisterRecipesEvent(RecipeManager manager, List<Recipe<?>> recipes) {
        this.manager = manager;
        this.recipes = recipes;
    }

    public RecipeManager getRecipeManager() {
        return this.manager;
    }

    public void register(Recipe<?> recipe) {
        System.out.println("Registering recipe: " + recipe.getId());
        this.recipes.add(recipe);
    }
}

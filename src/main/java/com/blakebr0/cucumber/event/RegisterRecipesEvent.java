package com.blakebr0.cucumber.event;

import com.blakebr0.cucumber.helper.RecipeHelper;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraftforge.eventbus.api.Event;

public class RegisterRecipesEvent extends Event {
    private final RecipeManager manager;

    public RegisterRecipesEvent(RecipeManager manager) {
        this.manager = manager;
    }

    public RecipeManager getRecipeManager() {
        return this.manager;
    }

    public void register(Recipe<?> recipe) {
        RecipeHelper.addRecipe(recipe);
    }
}

package com.blakebr0.cucumber.inventory;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeType;

public class CachedRecipe<I extends RecipeInput, T extends Recipe<I>> {
    private final RecipeType<T> type;
    private T recipe;

    public CachedRecipe(RecipeType<T> type) {
        this.type = type;
    }

    /**
     * Checks if the provided inventory matches the cached recipe. If it doesn't match, it will try to find a new recipe
     * @param inventory the recipe inventory
     * @param level the level
     * @return does this recipe match the inventory?
     */
    public boolean check(I inventory, ServerLevel level) {
        if (this.recipe != null && this.recipe.matches(inventory, level))
            return true;

        this.recipe = level.recipeAccess()
                .getRecipeFor(this.type, inventory, level)
                .map(RecipeHolder::value)
                .orElse(null);

        return this.recipe != null;
    }

    public boolean exists() {
        return this.recipe != null;
    }

    public T get() {
        return this.recipe;
    }

    public T checkAndGet(I inventory, ServerLevel level) {
        if (this.check(inventory, level))
            return this.recipe;

        return null;
    }
}

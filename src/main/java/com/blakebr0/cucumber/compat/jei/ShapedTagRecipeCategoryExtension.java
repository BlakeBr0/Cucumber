package com.blakebr0.cucumber.compat.jei;

import com.blakebr0.cucumber.crafting.recipe.ShapedTagRecipe;
import mezz.jei.api.recipe.category.extensions.vanilla.crafting.ICraftingCategoryExtension;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.display.ShapedCraftingRecipeDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay;

import java.util.List;

public class ShapedTagRecipeCategoryExtension implements ICraftingCategoryExtension<ShapedTagRecipe> {
    @Override
    public int getWidth(RecipeHolder<ShapedTagRecipe> recipeHolder) {
        var recipe = recipeHolder.value();
        return recipe.getWidth();
    }

    @Override
    public int getHeight(RecipeHolder<ShapedTagRecipe> recipeHolder) {
        var recipe = recipeHolder.value();
        return recipe.getHeight();
    }

    @Override
    public boolean isHandled(RecipeHolder<ShapedTagRecipe> recipeHolder) {
        var recipe = recipeHolder.value();
        if (recipe.isSpecial())
            return false;

        var displays = recipeHolder.value().display();
        if (displays.isEmpty())
            return false;

        var display = displays.getFirst();
        return display instanceof ShapedCraftingRecipeDisplay;
    }

    @Override
    public List<SlotDisplay> getIngredients(RecipeHolder<ShapedTagRecipe> recipeHolder) {
        var displays = recipeHolder.value().display();
        if (displays.isEmpty()) {
            return List.of();
        }

        var display = displays.getFirst();
        if (display instanceof ShapedCraftingRecipeDisplay shapedCraftingRecipeDisplay) {
            return shapedCraftingRecipeDisplay.ingredients();
        }

        return List.of();
    }
}

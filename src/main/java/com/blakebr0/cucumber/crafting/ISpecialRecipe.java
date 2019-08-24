package com.blakebr0.cucumber.crafting;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.RecipeMatcher;
import net.minecraftforge.items.IItemHandler;

public interface ISpecialRecipe {
    ItemStack getOutput();
    NonNullList<Ingredient> getIngredients();
    ResourceLocation getId();
    ISpecialRecipeSerializer<?> getSerializer();
    ISpecialRecipeType<?> getType();

    default boolean matches(IItemHandler inventory) {
        return this.matches(inventory, 0, inventory.getSlots());
    }

    default boolean matches(IItemHandler inventory, int startIndex, int endIndex) {
        NonNullList<ItemStack> inputs = NonNullList.create();
        for (int i = startIndex; i < endIndex; i++) {
            inputs.add(inventory.getStackInSlot(i));
        }

        return RecipeMatcher.findMatches(inputs, this.getIngredients()) != null;
    }

    default NonNullList<ItemStack> getRemainingItems(IItemHandler inventory) {
        NonNullList<ItemStack> remaining = NonNullList.withSize(inventory.getSlots(), ItemStack.EMPTY);
        for (int i = 0; i < remaining.size(); i++) {
            ItemStack stack = inventory.getStackInSlot(i);
            if (stack.hasContainerItem())
                remaining.set(i, stack.getContainerItem());
        }

        return remaining;
    }
}

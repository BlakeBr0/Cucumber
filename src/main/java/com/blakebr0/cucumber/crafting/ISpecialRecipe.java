package com.blakebr0.cucumber.crafting;

import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.RecipeMatcher;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

public interface ISpecialRecipe extends Recipe<Container> {
    @Override
    default ItemStack assemble(Container inventory, RegistryAccess access) {
        return this.assemble(new InvWrapper(inventory), access);
    }

    @Override
    default boolean matches(Container inventory, Level level) {
        return this.matches(new InvWrapper(inventory));
    }

    @Override
    default NonNullList<ItemStack> getRemainingItems(Container inventory) {
        return this.getRemainingItems(new InvWrapper(inventory));
    }

    ItemStack assemble(IItemHandler inventory, RegistryAccess access);

    default boolean matches(IItemHandler inventory) {
        return this.matches(inventory, 0, inventory.getSlots());
    }

    default boolean matches(IItemHandler inventory, int startIndex, int endIndex) {
        NonNullList<ItemStack> inputs = NonNullList.create();

        for (var i = startIndex; i < endIndex; i++) {
            inputs.add(inventory.getStackInSlot(i));
        }

        return RecipeMatcher.findMatches(inputs, this.getIngredients()) != null;
    }

    default NonNullList<ItemStack> getRemainingItems(IItemHandler inventory) {
        var remaining = NonNullList.withSize(inventory.getSlots(), ItemStack.EMPTY);

        for (int i = 0; i < remaining.size(); i++) {
            var stack = inventory.getStackInSlot(i);

            if (stack.hasCraftingRemainingItem())
                remaining.set(i, stack.getCraftingRemainingItem());
        }

        return remaining;
    }
}

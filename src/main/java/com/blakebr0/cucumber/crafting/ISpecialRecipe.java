package com.blakebr0.cucumber.crafting;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.common.util.RecipeMatcher;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

public interface ISpecialRecipe extends IRecipe<IInventory> {
    @Override
    default ItemStack getCraftingResult(IInventory inv) {
        return this.getCraftingResult(new InvWrapper(inv));
    }

    @Override
    default boolean matches(IInventory inv, World world) {
        return this.matches(new InvWrapper(inv));
    }

    @Override
    default NonNullList<ItemStack> getRemainingItems(IInventory inv) {
        return this.getRemainingItems(new InvWrapper(inv));
    }

    ItemStack getCraftingResult(IItemHandler inventory);

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

package com.blakebr0.cucumber.inventory;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.item.ItemStacksResourceHandler;

public class RecipeInventory implements Container {
    private final ItemStacksResourceHandler inventory;
    private final int start;
    private final int size;

    public RecipeInventory(ItemStacksResourceHandler inventory) {
        this(inventory, 0, inventory.size());
    }

    public RecipeInventory(ItemStacksResourceHandler inventory, int start, int size) {
        this.inventory = inventory;
        this.start = start;
        this.size = size;
    }

    @Override
    public int getContainerSize() {
        return this.size;
    }

    @Override
    public ItemStack getItem(int slot) {
        return this.inventory.getResource(slot + this.start).toStack();
    }

    @Override
    public ItemStack removeItem(int slot, int count) {
        var stack = this.inventory.getResource(slot + this.start);
        return stack.isEmpty() ? ItemStack.EMPTY : stack.toStack().split(count);
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        this.inventory.set(slot + this.start, ItemResource.of(stack), stack.getCount());
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        var stack = this.getItem(slot + this.start);
        if (stack.isEmpty()) return ItemStack.EMPTY;
        this.setItem(slot + this.start, ItemStack.EMPTY);
        return stack;
    }

    @Override
    public boolean isEmpty() {
        for (int i = this.start; i < this.size; i++) {
            if (!this.inventory.getResource(i).isEmpty())
                return false;
        }

        return true;
    }

    @Override
    public boolean canPlaceItem(int slot, ItemStack stack) {
        return this.inventory.isValid(slot + this.start, ItemResource.of(stack));
    }

    @Override
    public void clearContent() {
        for (int i = this.start; i < this.size; i++) {
            this.inventory.set(i, ItemResource.EMPTY, 0);
        }
    }

    @Override
    public int getMaxStackSize() { return 0; }
    @Override
    public void setChanged() { }
    @Override
    public boolean stillValid(Player player) { return false; }
}

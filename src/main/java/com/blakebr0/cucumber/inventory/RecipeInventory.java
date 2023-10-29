package com.blakebr0.cucumber.inventory;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;

public class RecipeInventory implements Container {
    private final IItemHandlerModifiable inventory;
    private final int start;
    private final int size;

    public RecipeInventory(IItemHandlerModifiable inventory, int start, int size) {
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
        return this.inventory.getStackInSlot(slot + this.start);
    }

    @Override
    public ItemStack removeItem(int slot, int count) {
        var stack = this.inventory.getStackInSlot(slot + this.start);
        return stack.isEmpty() ? ItemStack.EMPTY : stack.split(count);
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        this.inventory.setStackInSlot(slot + this.start, stack);
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
            if (!this.inventory.getStackInSlot(i).isEmpty())
                return false;
        }

        return true;
    }

    @Override
    public boolean canPlaceItem(int slot, ItemStack stack) {
        return this.inventory.isItemValid(slot + this.start, stack);
    }

    @Override
    public void clearContent() {
        for (int i = this.start; i < this.size; i++) {
            this.inventory.setStackInSlot(i, ItemStack.EMPTY);
        }
    }

    @Override
    public int getMaxStackSize() { return 0; }
    @Override
    public void setChanged() { }
    @Override
    public boolean stillValid(Player player) { return false; }
    @Override
    public void startOpen(Player player) { }
    @Override
    public void stopOpen(Player player) { }
}

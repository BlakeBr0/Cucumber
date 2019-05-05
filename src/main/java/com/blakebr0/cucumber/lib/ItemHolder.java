package com.blakebr0.cucumber.lib;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemHolder<T extends Item> {
    private final T item;

    public ItemHolder(T item) {
        this.item = item;
    }

    public T getItem() {
        return this.item;
    }

    public ItemStack getItemStack() {
        return new ItemStack(this.item);
    }

    public ItemStack getItemStack(int count) {
        return new ItemStack(this.item, count);
    }
}

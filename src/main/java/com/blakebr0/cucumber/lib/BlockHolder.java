package com.blakebr0.cucumber.lib;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class BlockHolder<T extends Block> {
    private final T block;

    public BlockHolder(T block) {
        this.block = block;
    }

    public T getBlock() {
        return this.block;
    }

    public ItemStack getItemStack() {
        return new ItemStack(this.block);
    }

    public ItemStack getItemStack(int count) {
        return new ItemStack(this.block, count);
    }
}

package com.blakebr0.cucumber.lib;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemPlaceholder {
	
	private ItemStack stack = ItemStack.EMPTY;
	private String ore = "";
	
	private ItemPlaceholder(ItemStack stack) {
		this.stack = stack;
	}
	
	private ItemPlaceholder(String ore) {
		this.ore = ore;
	}
	
	public static ItemPlaceholder of(Block block) {
		return of(new ItemStack(block));
	}
	
	public static ItemPlaceholder of(Item item) {
		return of(new ItemStack(item));
	}
	
	public static ItemPlaceholder of(ItemStack stack) {
		return new ItemPlaceholder(stack);
	}
	
	public static ItemPlaceholder of(String ore) {
		return new ItemPlaceholder(ore);
	}
	
	public Object getValue() {
		return !ore.isEmpty() ? ore : stack;
	}
	
	public ItemStack getStack() {
		return stack;
	}
	
	public String getOreName() {
		return ore;
	}
}

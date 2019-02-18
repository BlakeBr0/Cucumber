package com.blakebr0.cucumber.item;

import net.minecraft.item.Item;

public class ItemBase extends Item {

	public ItemBase(String name, Properties properties) {
		super(properties);
		this.setRegistryName(name);
	}
}
package com.blakebr0.cucumber.item;

import net.minecraft.item.Item;

import java.util.function.Function;

public class ItemBase extends Item {
	public ItemBase(String name, Function<Properties, Properties> properties) {
		super(properties.apply(new Properties()));
		this.setRegistryName(name);
	}
}
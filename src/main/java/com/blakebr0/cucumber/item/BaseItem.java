package com.blakebr0.cucumber.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;

import java.util.function.Function;

public class BaseItem extends Item {
	public BaseItem(Identifier id) {
		super(new Properties().setId(ResourceKey.create(Registries.ITEM, id)));
	}

	public BaseItem(Identifier id, Function<Properties, Properties> properties) {
		super(properties.apply(new Properties().setId(ResourceKey.create(Registries.ITEM, id))));
	}
}
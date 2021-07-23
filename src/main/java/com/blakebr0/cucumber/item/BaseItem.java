package com.blakebr0.cucumber.item;

import com.blakebr0.cucumber.iface.IEnableable;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.NonNullList;

import java.util.function.Function;

import net.minecraft.world.item.Item.Properties;

public class BaseItem extends Item {
	public BaseItem(Function<Properties, Properties> properties) {
		super(properties.apply(new Properties()));
	}

	@Override
	public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) {
		if (this instanceof IEnableable) {
			IEnableable enableable = (IEnableable) this;
			if (enableable.isEnabled())
				super.fillItemCategory(group, items);
		} else {
			super.fillItemCategory(group, items);
		}
	}
}
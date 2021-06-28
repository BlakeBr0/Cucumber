package com.blakebr0.cucumber.item;

import com.blakebr0.cucumber.iface.IEnableable;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import java.util.function.Function;

public class BaseItem extends Item {
	public BaseItem(Function<Properties, Properties> properties) {
		super(properties.apply(new Properties()));
	}

	@Override
	public void fillItemCategory(ItemGroup group, NonNullList<ItemStack> items) {
		if (this instanceof IEnableable) {
			IEnableable enableable = (IEnableable) this;
			if (enableable.isEnabled())
				super.fillItemCategory(group, items);
		} else {
			super.fillItemCategory(group, items);
		}
	}
}
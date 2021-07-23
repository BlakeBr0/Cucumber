package com.blakebr0.cucumber.item;

import net.minecraft.world.item.ItemStack;

import java.util.function.Function;

import net.minecraft.world.item.Item.Properties;

public class BaseShinyItem extends BaseItem {
    public BaseShinyItem(Function<Properties, Properties> properties) {
        super(properties);
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }
}

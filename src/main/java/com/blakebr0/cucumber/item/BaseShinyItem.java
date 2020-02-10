package com.blakebr0.cucumber.item;

import net.minecraft.item.ItemStack;

import java.util.function.Function;

public class BaseShinyItem extends BaseItem {
    public BaseShinyItem(Function<Properties, Properties> properties) {
        super(properties);
    }

    @Override
    public boolean hasEffect(ItemStack stack) {
        return true;
    }
}

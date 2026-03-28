package com.blakebr0.cucumber.item;

import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;

import java.util.function.Function;

public class BaseShinyItem extends BaseItem {
    public BaseShinyItem(Identifier id) {
        super(id);
    }

    public BaseShinyItem(Identifier id, Function<Properties, Properties> properties) {
        super(id, properties);
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }
}

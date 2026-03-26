package com.blakebr0.cucumber.item.tool;

import net.minecraft.world.item.FishingRodItem;

import java.util.function.Function;

public class BaseFishingRodItem extends FishingRodItem {
    public BaseFishingRodItem(Function<Properties, Properties> properties) {
        super(properties.apply(new Properties()));
    }
}

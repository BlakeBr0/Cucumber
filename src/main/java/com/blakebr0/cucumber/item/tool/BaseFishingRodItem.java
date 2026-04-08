package com.blakebr0.cucumber.item.tool;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.FishingRodItem;

import java.util.function.Function;

public class BaseFishingRodItem extends FishingRodItem {
    public BaseFishingRodItem(Identifier id, Function<Properties, Properties> properties) {
        super(properties.apply(new Properties().stacksTo(1).setId(ResourceKey.create(Registries.ITEM, id))));
    }
}

package com.blakebr0.cucumber.item.tool;

import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ShearsItem;

import java.util.function.Function;

public class BaseShearsItem extends ShearsItem {
    public BaseShearsItem(Identifier id, Function<Properties, Properties> properties) {
        super(properties.apply(new Properties()
                .setId(ResourceKey.create(Registries.ITEM, id))
                .component(DataComponents.TOOL, ShearsItem.createToolProperties()))
        );
    }
}

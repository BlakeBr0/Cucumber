package com.blakebr0.cucumber.item.tool;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ShearsItem;

import java.util.function.Function;

public class BaseShearsItem extends ShearsItem {
    public BaseShearsItem(Function<Properties, Properties> properties) {
        super(properties.apply(new Properties().component(DataComponents.TOOL, ShearsItem.createToolProperties())));
    }
}

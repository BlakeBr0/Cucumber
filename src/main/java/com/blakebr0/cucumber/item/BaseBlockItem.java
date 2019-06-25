package com.blakebr0.cucumber.item;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;

import java.util.function.Function;

public class BaseBlockItem extends BlockItem {
    public BaseBlockItem(Block block, Function<Properties, Properties> properties) {
        super(block, properties.apply(new Properties()));
    }
}

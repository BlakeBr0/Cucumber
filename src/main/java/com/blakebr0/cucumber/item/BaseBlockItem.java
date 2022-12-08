package com.blakebr0.cucumber.item;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;

import java.util.function.Function;

public class BaseBlockItem extends BlockItem {
    public BaseBlockItem(Block block) {
        super(block, new Properties());
    }

    public BaseBlockItem(Block block, Function<Properties, Properties> properties) {
        super(block, properties.apply(new Properties()));
    }
}

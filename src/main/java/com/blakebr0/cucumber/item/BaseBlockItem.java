package com.blakebr0.cucumber.item;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.util.ResourceLocation;

import java.util.function.Function;

public class BaseBlockItem extends BlockItem {
    public BaseBlockItem(Block block, Function<Properties, Properties> properties) {
        super(block, properties.apply(new Properties()));

        ResourceLocation name = block.getRegistryName();
        if (name != null)
            this.setRegistryName(name);
    }
}

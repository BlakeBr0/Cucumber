package com.blakebr0.cucumber.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;

import java.util.function.Function;

public class BaseBlockItem extends BlockItem {
    public BaseBlockItem(Identifier id, Block block) {
        super(block, new Properties().setId(ResourceKey.create(Registries.ITEM, id)));
    }

    public BaseBlockItem(Identifier id, Block block, Function<Properties, Properties> properties) {
        super(block, properties.apply(new Properties().setId(ResourceKey.create(Registries.ITEM, id))));
    }
}

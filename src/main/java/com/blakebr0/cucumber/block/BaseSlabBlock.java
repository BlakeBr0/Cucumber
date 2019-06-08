package com.blakebr0.cucumber.block;

import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;

public class BaseSlabBlock extends SlabBlock {
    public BaseSlabBlock(String name, Block block) {
        this(name, Properties.from(block));
    }

    public BaseSlabBlock(String name, Properties properties) {
        super(properties);
        this.setRegistryName(name);
    }
}

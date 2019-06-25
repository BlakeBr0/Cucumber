package com.blakebr0.cucumber.block;

import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;

public class BaseSlabBlock extends SlabBlock {
    public BaseSlabBlock(Block block) {
        this(Properties.from(block));
    }

    public BaseSlabBlock(Properties properties) {
        super(properties);
    }
}

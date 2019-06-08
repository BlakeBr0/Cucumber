package com.blakebr0.cucumber.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.StairsBlock;

public class BaseStairsBlock extends StairsBlock {
    public BaseStairsBlock(String name, Block block) {
        this(name, block, Properties.from(block));
    }

    public BaseStairsBlock(String name, Block block, Properties properties) {
        this(name, block.getDefaultState(), properties);
    }

    public BaseStairsBlock(String name, BlockState state, Properties properties) {
        super(state, properties);
        this.setRegistryName(name);
    }
}

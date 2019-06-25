package com.blakebr0.cucumber.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.StairsBlock;

public class BaseStairsBlock extends StairsBlock {
    public BaseStairsBlock(Block block) {
        this(block, Properties.from(block));
    }

    public BaseStairsBlock(Block block, Properties properties) {
        this(block.getDefaultState(), properties);
    }

    public BaseStairsBlock(BlockState state, Properties properties) {
        super(state, properties);
    }
}

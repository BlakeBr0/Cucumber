package com.blakebr0.cucumber.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.state.IBlockState;

public class BlockStairsBase extends BlockStairs {
    public BlockStairsBase(String name, Block block) {
        this(name, block, Properties.from(block));
    }

    public BlockStairsBase(String name, Block block, Properties properties) {
        this(name, block.getDefaultState(), properties);
    }

    public BlockStairsBase(String name, IBlockState state, Properties properties) {
        super(state, properties);
        this.setRegistryName(name);
    }
}

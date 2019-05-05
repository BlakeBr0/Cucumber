package com.blakebr0.cucumber.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;

public class BlockSlabBase extends BlockSlab {
    public BlockSlabBase(String name, Block block) {
        this(name, Properties.from(block));
    }

    public BlockSlabBase(String name, Properties properties) {
        super(properties);
        this.setRegistryName(name);
    }
}

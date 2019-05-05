package com.blakebr0.cucumber.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockWall;

public class BlockWallBase extends BlockWall {
    public BlockWallBase(String name, Block block) {
        this(name, Properties.from(block));
    }

    public BlockWallBase(String name, Properties properties) {
        super(properties);
        this.setRegistryName(name);
    }
}

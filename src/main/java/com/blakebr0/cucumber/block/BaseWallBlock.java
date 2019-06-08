package com.blakebr0.cucumber.block;

import net.minecraft.block.Block;
import net.minecraft.block.WallBlock;

public class BaseWallBlock extends WallBlock {
    public BaseWallBlock(String name, Block block) {
        this(name, Properties.from(block));
    }

    public BaseWallBlock(String name, Properties properties) {
        super(properties);
        this.setRegistryName(name);
    }
}

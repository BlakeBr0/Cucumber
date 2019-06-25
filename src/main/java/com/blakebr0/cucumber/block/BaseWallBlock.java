package com.blakebr0.cucumber.block;

import net.minecraft.block.Block;
import net.minecraft.block.WallBlock;

public class BaseWallBlock extends WallBlock {
    public BaseWallBlock(Block block) {
        this(Properties.from(block));
    }

    public BaseWallBlock(Properties properties) {
        super(properties);
    }
}

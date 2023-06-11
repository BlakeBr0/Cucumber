package com.blakebr0.cucumber.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.WallBlock;

public class BaseWallBlock extends WallBlock {
    public BaseWallBlock(Block block) {
        this(Properties.copy(block));
    }

    public BaseWallBlock(Properties properties) {
        super(properties);
    }

    public BaseWallBlock(SoundType sound, float hardness, float resistance) {
        this(Properties.of()
                .sound(sound)
                .strength(hardness, resistance)
        );
    }

    public BaseWallBlock(SoundType sound, float hardness, float resistance, boolean tool) {
        this(Properties.of()
                .sound(sound)
                .strength(hardness, resistance)
                .requiresCorrectToolForDrops()
        );
    }
}

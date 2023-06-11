package com.blakebr0.cucumber.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;

public class BaseSlabBlock extends SlabBlock {
    public BaseSlabBlock(Block block) {
        this(Properties.copy(block));
    }

    public BaseSlabBlock(Properties properties) {
        super(properties);
    }

    public BaseSlabBlock(SoundType sound, float hardness, float resistance) {
        this(Properties.of()
                .sound(sound)
                .strength(hardness, resistance)
        );
    }

    public BaseSlabBlock(SoundType sound, float hardness, float resistance, boolean tool) {
        this(Properties.of()
                .sound(sound)
                .strength(hardness, resistance)
                .requiresCorrectToolForDrops()
        );
    }
}

package com.blakebr0.cucumber.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;

import java.util.function.Supplier;

public class BaseSlabBlock extends SlabBlock {
    public BaseSlabBlock(Supplier<Block> block) {
        this(Properties.ofFullCopy(block.get()));
    }

    public BaseSlabBlock(Properties properties) {
        super(properties);
    }

    public BaseSlabBlock(SoundType sound, float hardness, float resistance) {
        this(sound, hardness, resistance, false);
    }

    public BaseSlabBlock(SoundType sound, float hardness, float resistance, boolean tool) {
        var properties = Properties.of().sound(sound).strength(hardness, resistance);

        if (tool) {
            properties.requiresCorrectToolForDrops();
        }

        this(properties);
    }
}

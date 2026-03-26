package com.blakebr0.cucumber.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.WallBlock;

import java.util.function.Supplier;

public class BaseWallBlock extends WallBlock {
    public BaseWallBlock(Supplier<Block> block) {
        this(Properties.ofFullCopy(block.get()));
    }

    public BaseWallBlock(Properties properties) {
        super(properties);
    }

    public BaseWallBlock(SoundType sound, float hardness, float resistance) {
        this(sound, hardness, resistance, false);
    }

    public BaseWallBlock(SoundType sound, float hardness, float resistance, boolean tool) {
        var properties = Properties.of()
                .sound(sound)
                .strength(hardness, resistance);

        if (tool) {
            properties.requiresCorrectToolForDrops();
        }

        this(properties);
    }
}

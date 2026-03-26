package com.blakebr0.cucumber.block;

import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Function;

public class BaseStairsBlock extends StairBlock {
    public BaseStairsBlock(BlockState state) {
        this(state, Properties.ofFullCopy(state.getBlock()));
    }

    public BaseStairsBlock(BlockState state, Properties properties) {
        super(state, properties);
    }

    public BaseStairsBlock(BlockState state, Function<Properties, Properties> properties) {
        super(state, properties.apply(Properties.of()));
    }

    public BaseStairsBlock(BlockState state, SoundType sound, float hardness, float resistance) {
        this(state, sound, hardness, resistance, false);
    }

    public BaseStairsBlock(BlockState state, SoundType sound, float hardness, float resistance, boolean tool) {
        var properties = Properties.of()
                .sound(sound)
                .strength(hardness, resistance);

        if (tool) {
            properties.requiresCorrectToolForDrops();
        }

        this(state, properties);
    }
}

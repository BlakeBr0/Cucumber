package com.blakebr0.cucumber.block;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Function;

public class BaseStairsBlock extends StairBlock {
    public BaseStairsBlock(Identifier id, BlockState state) {
        this(id, state, Properties.ofFullCopy(state.getBlock()));
    }

    public BaseStairsBlock(Identifier id, BlockState state, Properties properties) {
        super(state, properties.setId(ResourceKey.create(Registries.BLOCK, id)));
    }

    public BaseStairsBlock(Identifier id, BlockState state, Function<Properties, Properties> properties) {
        super(state, properties.apply(Properties.of().setId(ResourceKey.create(Registries.BLOCK, id))));
    }

    public BaseStairsBlock(Identifier id, BlockState state, SoundType sound, float hardness, float resistance) {
        this(id, state, sound, hardness, resistance, false);
    }

    public BaseStairsBlock(Identifier id, BlockState state, SoundType sound, float hardness, float resistance, boolean tool) {
        var properties = Properties.of()
                .sound(sound)
                .strength(hardness, resistance);

        if (tool) {
            properties.requiresCorrectToolForDrops();
        }

        this(id, state, properties);
    }
}

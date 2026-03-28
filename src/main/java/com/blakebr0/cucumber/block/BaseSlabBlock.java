package com.blakebr0.cucumber.block;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;

import java.util.function.Supplier;

public class BaseSlabBlock extends SlabBlock {
    public BaseSlabBlock(Identifier id, Supplier<Block> block) {
        this(id, Properties.ofFullCopy(block.get()));
    }

    public BaseSlabBlock(Identifier id, Properties properties) {
        super(properties.setId(ResourceKey.create(Registries.BLOCK, id)));
    }

    public BaseSlabBlock(Identifier id, SoundType sound, float hardness, float resistance) {
        this(id, sound, hardness, resistance, false);
    }

    public BaseSlabBlock(Identifier id, SoundType sound, float hardness, float resistance, boolean tool) {
        var properties = Properties.of().sound(sound).strength(hardness, resistance);

        if (tool) {
            properties.requiresCorrectToolForDrops();
        }

        this(id, properties);
    }
}

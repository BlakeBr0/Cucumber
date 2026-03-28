package com.blakebr0.cucumber.block;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.WallBlock;

import java.util.function.Supplier;

public class BaseWallBlock extends WallBlock {
    public BaseWallBlock(Identifier id, Supplier<Block> block) {
        this(id, Properties.ofFullCopy(block.get()));
    }

    public BaseWallBlock(Identifier id, Properties properties) {
        super(properties.setId(ResourceKey.create(Registries.BLOCK, id)));
    }

    public BaseWallBlock(Identifier id, SoundType sound, float hardness, float resistance) {
        this(id, sound, hardness, resistance, false);
    }

    public BaseWallBlock(Identifier id, SoundType sound, float hardness, float resistance, boolean tool) {
        var properties = Properties.of()
                .sound(sound)
                .strength(hardness, resistance);

        if (tool) {
            properties.requiresCorrectToolForDrops();
        }

        this(id, properties);
    }
}

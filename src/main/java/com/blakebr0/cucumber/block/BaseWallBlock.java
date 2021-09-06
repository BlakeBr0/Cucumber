package com.blakebr0.cucumber.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.material.Material;

public class BaseWallBlock extends WallBlock {
    public BaseWallBlock(Block block) {
        this(Properties.copy(block));
    }

    public BaseWallBlock(Properties properties) {
        super(properties);
    }

    public BaseWallBlock(Material material, SoundType sound, float hardness, float resistance) {
        this(Properties.of(material)
                .sound(sound)
                .strength(hardness, resistance)
        );
    }

    public BaseWallBlock(Material material, SoundType sound, float hardness, float resistance, boolean tool) {
        this(Properties.of(material)
                .sound(sound)
                .strength(hardness, resistance)
                .requiresCorrectToolForDrops()
        );
    }
}

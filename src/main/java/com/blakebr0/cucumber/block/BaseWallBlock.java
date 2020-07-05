package com.blakebr0.cucumber.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.WallBlock;
import net.minecraft.block.material.Material;

public class BaseWallBlock extends WallBlock {
    public BaseWallBlock(Block block) {
        this(Properties.from(block));
    }

    public BaseWallBlock(Properties properties) {
        super(properties);
    }

    public BaseWallBlock(Material material, SoundType sound, float hardness, float resistance) {
        this(Properties.create(material).sound(sound).hardnessAndResistance(hardness, resistance));
    }
}

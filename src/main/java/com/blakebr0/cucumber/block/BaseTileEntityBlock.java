package com.blakebr0.cucumber.block;

import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;

import java.util.function.Function;

public abstract class BaseTileEntityBlock extends BaseBlock implements EntityBlock {
    public BaseTileEntityBlock(Material material, Function<Properties, Properties> properties) {
        super(material, properties);
    }

    public BaseTileEntityBlock(Material material, SoundType sound, float hardness, float resistance) {
        super(material, sound, hardness, resistance);
    }

    public BaseTileEntityBlock(Material material, SoundType sound, float hardness, float resistance, boolean tool) {
        super(material, sound, hardness, resistance, tool);
    }
}

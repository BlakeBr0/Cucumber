package com.blakebr0.cucumber.block;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.common.ToolType;

import java.util.function.Function;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

// TODO: 1.17 delete?
public class BaseTileEntityBlock extends BaseBlock {
    public BaseTileEntityBlock(Material material, Function<Properties, Properties> properties) {
        super(material, properties);
    }

    public BaseTileEntityBlock(Material material, SoundType sound, float hardness, float resistance) {
        super(material, sound, hardness, resistance);
    }

    public BaseTileEntityBlock(Material material, SoundType sound, float hardness, float resistance, ToolType tool) {
        super(material, sound, hardness, resistance, tool);
    }
}

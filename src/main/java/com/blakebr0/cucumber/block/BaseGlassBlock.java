package com.blakebr0.cucumber.block;

import net.minecraft.block.GlassBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

import java.util.function.Function;

public class BaseGlassBlock extends GlassBlock {
    public BaseGlassBlock(Material material, Function<Properties, Properties> properties) {
        super(properties.apply(Properties.create(material)).notSolid());
    }

    public BaseGlassBlock(Material material, SoundType sound, float hardness, float resistance) {
        super(Properties.create(material).sound(sound).hardnessAndResistance(hardness, resistance).notSolid());
    }
}

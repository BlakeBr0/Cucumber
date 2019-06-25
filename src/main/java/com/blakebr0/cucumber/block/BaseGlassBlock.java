package com.blakebr0.cucumber.block;

import net.minecraft.block.GlassBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.BlockRenderLayer;

import java.util.function.Function;

public class BaseGlassBlock extends GlassBlock {
    private final boolean translucent;

    public BaseGlassBlock(Material material, Function<Properties, Properties> properties) {
        this(material, properties, true);
    }

    public BaseGlassBlock(Material material, SoundType sound, float hardness, float resistance) {
        this(material, sound, hardness, resistance, true);
    }

    public BaseGlassBlock(Material material, Function<Properties, Properties> properties, boolean translucent) {
        super(properties.apply(Properties.create(material)));
        this.translucent = translucent;
    }

    public BaseGlassBlock(Material material, SoundType sound, float hardness, float resistance, boolean translucent) {
        super(Properties.create(material).sound(sound).hardnessAndResistance(hardness, resistance));
        this.translucent = translucent;
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return this.translucent ? BlockRenderLayer.TRANSLUCENT : BlockRenderLayer.CUTOUT;
    }
}

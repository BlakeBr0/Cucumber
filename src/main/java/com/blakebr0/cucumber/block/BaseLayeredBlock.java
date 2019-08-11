package com.blakebr0.cucumber.block;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.BlockRenderLayer;

import java.util.function.Function;

public class BaseLayeredBlock extends BaseBlock {
    private final boolean translucent;

    public BaseLayeredBlock(Material material, Function<Properties, Properties> properties) {
        this(material, properties, false);
    }

    public BaseLayeredBlock(Material material, SoundType sound, float hardness, float resistance) {
        this(material, sound, hardness, resistance, false);
    }

    public BaseLayeredBlock(Material material, Function<Properties, Properties> properties, boolean translucent) {
        super(material, properties);
        this.translucent = translucent;
    }

    public BaseLayeredBlock(Material material, SoundType sound, float hardness, float resistance, boolean translucent) {
        super(material, sound, hardness, resistance);
        this.translucent = translucent;
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return this.translucent ? BlockRenderLayer.TRANSLUCENT : BlockRenderLayer.CUTOUT;
    }
}

package com.blakebr0.cucumber.block;

import net.minecraft.block.BlockGlass;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.BlockRenderLayer;

import java.util.function.Function;

public class BlockGlassBase extends BlockGlass {

    private final boolean translucent;

    public BlockGlassBase(String name, Material material, Function<Properties, Properties> properties) {
        this(name, material, properties, true);
    }

    public BlockGlassBase(String name, Material material, SoundType sound, float hardness, float resistance) {
        this(name, material, sound, hardness, resistance, true);
    }

    public BlockGlassBase(String name, Material material, Function<Properties, Properties> properties, boolean translucent) {
        super(properties.apply(Properties.create(material)));
        this.setRegistryName(name);
        this.translucent = translucent;
    }

    public BlockGlassBase(String name, Material material, SoundType sound, float hardness, float resistance, boolean translucent) {
        super(Properties.create(material).sound(sound).hardnessAndResistance(hardness, resistance));
        this.setRegistryName(name);
        this.translucent = translucent;
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return this.translucent ? BlockRenderLayer.TRANSLUCENT : BlockRenderLayer.CUTOUT;
    }
}

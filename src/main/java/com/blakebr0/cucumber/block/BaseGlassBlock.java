package com.blakebr0.cucumber.block;

import net.minecraft.world.level.block.GlassBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;

import java.util.function.Function;

public class BaseGlassBlock extends GlassBlock {
    // TODO: .isValidSpawn(Blocks::never).isRedstoneConductor(Blocks::never).isSuffocating(Blocks::never).isViewBlocking(Blocks::never)

    public BaseGlassBlock(Material material, Function<Properties, Properties> properties) {
        super(properties.apply(Properties.of(material)).noOcclusion());
    }

    public BaseGlassBlock(Material material, SoundType sound, float hardness, float resistance) {
        super(Properties.of(material).sound(sound).strength(hardness, resistance).noOcclusion());
    }

    public BaseGlassBlock(Material material, SoundType sound, float hardness, float resistance, boolean tool) {
        super(Properties.of(material).sound(sound).strength(hardness, resistance).requiresCorrectToolForDrops().noOcclusion());
    }
}

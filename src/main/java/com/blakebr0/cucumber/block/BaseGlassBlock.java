package com.blakebr0.cucumber.block;

import net.minecraft.block.GlassBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

import java.util.function.Function;

public class BaseGlassBlock extends GlassBlock {
    // TODO: .isValidSpawn(Blocks::never).isRedstoneConductor(Blocks::never).isSuffocating(Blocks::never).isViewBlocking(Blocks::never)

    public BaseGlassBlock(Material material, Function<Properties, Properties> properties) {
        super(properties.apply(Properties.of(material)).noOcclusion());
    }

    public BaseGlassBlock(Material material, SoundType sound, float hardness, float resistance) {
        super(Properties.of(material).sound(sound).strength(hardness, resistance).noOcclusion());
    }

    public BaseGlassBlock(Material material, SoundType sound, float hardness, float resistance, ToolType tool) {
        super(Properties.of(material).sound(sound).strength(hardness, resistance).harvestTool(tool).requiresCorrectToolForDrops().noOcclusion());
    }
}

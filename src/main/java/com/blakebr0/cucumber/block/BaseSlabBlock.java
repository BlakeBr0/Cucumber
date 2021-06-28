package com.blakebr0.cucumber.block;

import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

public class BaseSlabBlock extends SlabBlock {
    public BaseSlabBlock(Block block) {
        this(Properties.copy(block));
    }

    public BaseSlabBlock(Properties properties) {
        super(properties);
    }

    public BaseSlabBlock(Material material, SoundType sound, float hardness, float resistance) {
        this(Properties.of(material).sound(sound).strength(hardness, resistance));
    }

    public BaseSlabBlock(Material material, SoundType sound, float hardness, float resistance, ToolType tool) {
        this(Properties.of(material).sound(sound).strength(hardness, resistance).harvestTool(tool).requiresCorrectToolForDrops());
    }
}

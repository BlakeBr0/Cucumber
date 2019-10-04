package com.blakebr0.cucumber.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.material.Material;

import java.util.function.Supplier;

public class BaseStairsBlock extends StairsBlock {
    public BaseStairsBlock(Block block) {
        this(block, Properties.from(block));
    }

    public BaseStairsBlock(Block block, Properties properties) {
        this(block::getDefaultState, properties);
    }

    public BaseStairsBlock(Supplier<BlockState> state, Properties properties) {
        super(state, properties);
    }

    public BaseStairsBlock(Supplier<BlockState> state, Material material, SoundType sound, float hardness, float resistance) {
        this(state, Properties.create(material).sound(sound).hardnessAndResistance(hardness, resistance));
    }
}

package com.blakebr0.cucumber.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.common.ToolType;

import java.util.function.Function;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class BaseBlock extends Block {
	public BaseBlock(Material material, Function<Properties, Properties> properties) {
		super(properties.apply(Properties.of(material)));
	}

	public BaseBlock(Material material, SoundType sound, float hardness, float resistance) {
		super(Properties.of(material).sound(sound).strength(hardness, resistance));
	}

	public BaseBlock(Material material, SoundType sound, float hardness, float resistance, ToolType tool) {
		super(Properties.of(material).sound(sound).strength(hardness, resistance).harvestTool(tool).requiresCorrectToolForDrops());
	}
}
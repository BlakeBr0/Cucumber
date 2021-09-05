package com.blakebr0.cucumber.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;

import java.util.function.Function;

public class BaseBlock extends Block {
	public BaseBlock(Material material, Function<Properties, Properties> properties) {
		super(properties.apply(Properties.of(material)));
	}

	public BaseBlock(Material material, SoundType sound, float hardness, float resistance) {
		super(Properties.of(material).sound(sound).strength(hardness, resistance));
	}

	public BaseBlock(Material material, SoundType sound, float hardness, float resistance, boolean tool) {
		super(Properties.of(material).sound(sound).strength(hardness, resistance).requiresCorrectToolForDrops());
	}
}
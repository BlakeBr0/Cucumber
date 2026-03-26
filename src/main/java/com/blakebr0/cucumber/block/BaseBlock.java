package com.blakebr0.cucumber.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;

import java.util.function.Function;

public class BaseBlock extends Block {
	public BaseBlock(Function<Properties, Properties> properties) {
		super(properties.apply(Properties.of()));
	}

	public BaseBlock(SoundType sound, float hardness, float resistance) {
		this(sound, hardness, resistance, false);
	}

	public BaseBlock(SoundType sound, float hardness, float resistance, boolean tool) {
		var properties = Properties.of().sound(sound).strength(hardness, resistance);

		if (tool) {
			properties.requiresCorrectToolForDrops();
		}

		super(properties);
	}
}
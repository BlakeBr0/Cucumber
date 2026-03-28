package com.blakebr0.cucumber.block;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;

import java.util.function.Function;

public class BaseBlock extends Block {
	public BaseBlock(Identifier id, Function<Properties, Properties> properties) {
		super(properties.apply(Properties.of().setId(ResourceKey.create(Registries.BLOCK, id))));
	}

	public BaseBlock(Identifier id, SoundType sound, float hardness, float resistance) {
		this(id, sound, hardness, resistance, false);
	}

	public BaseBlock(Identifier id, SoundType sound, float hardness, float resistance, boolean tool) {
		var properties = Properties.of()
				.setId(ResourceKey.create(Registries.BLOCK, id))
				.sound(sound)
				.strength(hardness, resistance);

		if (tool) {
			properties.requiresCorrectToolForDrops();
		}

		super(properties);
	}
}
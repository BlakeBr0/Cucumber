package com.blakebr0.cucumber.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

import java.util.function.Function;

public class BlockBase extends Block {

	public BlockBase(String name, Material material, Function<Properties, Properties> properties) {
		super(properties.apply(Properties.create(material)));
		this.setRegistryName(name);
	}

	public BlockBase(String name, Material material, SoundType sound, float hardness, float resistance) {
		super(Properties.create(material).sound(sound).hardnessAndResistance(hardness, resistance));
		this.setRegistryName(name);
	}
}
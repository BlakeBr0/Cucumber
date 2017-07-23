package com.blakebr0.cucumber.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockBase extends Block {

	public BlockBase(String name, Material material, SoundType sound, float hardness, float resistance, String tool, int level) {
		super(material);
		this.setSoundType(sound);
		this.setUnlocalizedName(name);
		this.setHardness(hardness);
		this.setResistance(resistance);
		this.setHarvestLevel(tool, level);
	} 
	
	public BlockBase(String name, Material material, SoundType sound, float hardness, float resistance) {
		super(material);
		this.setSoundType(sound);
		this.setUnlocalizedName(name);
		this.setHardness(hardness);
		this.setResistance(resistance);
	}
	
	public void init(){
		
	}
}
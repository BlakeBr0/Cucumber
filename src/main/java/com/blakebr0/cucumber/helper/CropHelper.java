package com.blakebr0.cucumber.helper;

import java.lang.reflect.Method;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class CropHelper {

	private static final Method GET_SEED;
	private static final Method GET_CROP;
	
	static {
		GET_SEED = ReflectionHelper.findMethod(BlockCrops.class, "getSeed", "func_149866_i");
		GET_CROP = ReflectionHelper.findMethod(BlockCrops.class, "getCrop", "func_208486_d");
	}
	
	public static Item getSeed(Block block) {
		try {
			return (Item) GET_SEED.invoke(block);
		} catch (Exception e) {
			return null;
		}
	}
	
	public static Item getCrop(Block block) {
		try {
			return (Item) GET_CROP.invoke(block);
		} catch (Exception e) {
			return null;
		}
	}
}

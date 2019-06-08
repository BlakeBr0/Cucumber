package com.blakebr0.cucumber.helper;

import net.minecraft.block.CropsBlock;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.lang.reflect.Method;

public class CropHelper {
	private static final Method GET_SEED;

	static {
		GET_SEED = ObfuscationReflectionHelper.findMethod(CropsBlock.class, "func_149866_i");
	}
	
	public static Item getSeed(CropsBlock block) {
		try {
			return (Item) GET_SEED.invoke(block);
		} catch (Exception e) {
			return null;
		}
	}
}

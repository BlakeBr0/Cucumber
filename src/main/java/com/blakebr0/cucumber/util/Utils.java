package com.blakebr0.cucumber.util;

import java.text.NumberFormat;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.lwjgl.input.Keyboard;

import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.oredict.OreDictionary;

public class Utils {

	public static Random rand = new Random();

	public static String localize(String key) {
		return I18n.translateToLocal(key);
	}
	
	public static String localize(String key, Object... format) {
		return I18n.translateToLocalFormatted(key, format);
	}

	/**
	 * Adds commas to the specified numerical value
	 * 
	 * @param obj the number
	 * @return the formatted number
	 */
	public static String format(Object obj) {
		return NumberFormat.getInstance().format(obj);
	}

	public static boolean isShiftKeyDown() {
		if (Keyboard.isCreated()) {
			return Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT);
		} else {
			return false;
		}
	}

	public static List<String> asList(String string) {
		return Collections.singletonList(string);
	}

	public static int randInt(int min, int max) {
		return rand.nextInt(max - min + 1) + min;
	}

	@Deprecated
	public static ItemStack getItem(String oreDict, int stackSize) {
		ItemStack item = ItemStack.EMPTY;
		List<ItemStack> list = OreDictionary.getOres(oreDict);
		if (!list.isEmpty()) {
			item = list.get(0).copy();
			item.setCount(stackSize);
		}
		return item;
	}
	
	public static int intColor(int r, int g, int b) {
		return (r * 65536 + g * 256 + b);
	}
	
	public static int[] hexToRGB(int hex) {
		int[] colors = new int[3];
		
		int r = hex >> 16 & 255;
		int g = hex >> 8 & 255;
		int b = hex & 255;
		
		return colors;
	}
}

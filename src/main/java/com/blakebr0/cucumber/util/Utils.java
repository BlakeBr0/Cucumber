package com.blakebr0.cucumber.util;

import net.minecraft.client.util.InputMappings;
import net.minecraftforge.fml.ForgeI18n;

import java.text.NumberFormat;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Utils {

	public static Random rand = new Random();

	public static String localize(String key) {
		return ForgeI18n.parseMessage(key);
	}
	
	public static String localize(String key, Object... format) {
		return ForgeI18n.parseMessage(key, format);
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
		return InputMappings.isKeyDown(340) || InputMappings.isKeyDown(344);
	}

	public static List<String> asList(String string) {
		return Collections.singletonList(string);
	}

	public static int randInt(int min, int max) {
		return rand.nextInt(max - min + 1) + min;
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

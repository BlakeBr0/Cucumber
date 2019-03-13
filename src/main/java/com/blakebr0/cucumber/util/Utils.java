package com.blakebr0.cucumber.util;

import net.minecraft.client.util.InputMappings;
import net.minecraftforge.fml.ForgeI18n;

import java.awt.*;
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

		colors[0] = hex >> 16 & 255;
		colors[1] = hex >> 8 & 255;
		colors[2] = hex & 255;

		return colors;
	}

	private static float interpolate(float a, float b, float proportion) {
		return (a + ((b - a) * proportion));
	}

	public static int interpolateColor(int a, int b, float proportion) {
		float[] hsva = new float[3];
		float[] hsvb = new float[3];

		Color.RGBtoHSB((a >> 16) & 0xFF, (a >> 8) & 0xFF, a & 0xFF, hsva);
		Color.RGBtoHSB((b >> 16) & 0xFF, (b >> 8) & 0xFF, b & 0xFF, hsvb);

		for (int i = 0; i < 3; i++) {
			hsvb[i] = interpolate(hsva[i], hsvb[i], proportion);
		}

		float alpha = interpolate((a >> 24) & 0xFF, (b >> 24) & 0xFF, proportion);

		return Color.HSBtoRGB(hsvb[0], hsvb[1], hsvb[2]) | ((int) (alpha * 255) & 0xFF);
	}

	public static int saturate(int color, float saturation) {
		float[] hsv = new float[3];
		Color.RGBtoHSB((color >> 16) & 255, (color >> 8) & 255, color & 255, hsv);
		hsv[1] *= saturation;
		return Color.HSBtoRGB(hsv[0], hsv[1], hsv[2]);
	}

	public static int hexToIntWithAlpha(int hex, int alpha) {
		return alpha << 24 | hex & 0x00FFFFFF;
	}

	public static int calcAlpha(double current, double max) {
		return (int) ((max - current) / max) * 255;
	}
}

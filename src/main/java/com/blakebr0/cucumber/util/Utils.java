package com.blakebr0.cucumber.util;

import net.minecraft.util.RandomSource;

import java.text.NumberFormat;

public final class Utils {
	public static final RandomSource RANDOM = RandomSource.create();

	public static String format(Object obj) {
		return NumberFormat.getInstance().format(obj);
	}

	public static int randInt(int min, int max) {
		return RANDOM.nextInt(max - min + 1) + min;
	}
}

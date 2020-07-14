package com.blakebr0.cucumber.util;

import java.text.NumberFormat;
import java.util.Random;

public final class Utils {
	public static final Random RANDOM = new Random();

	public static String format(Object obj) {
		return NumberFormat.getInstance().format(obj);
	}

	public static int randInt(int min, int max) {
		return RANDOM.nextInt(max - min + 1) + min;
	}
}

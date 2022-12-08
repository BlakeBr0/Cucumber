package com.blakebr0.cucumber.util;

import net.minecraft.util.RandomSource;

public final class Utils {
	public static final RandomSource RANDOM = RandomSource.createThreadSafe();

	public static int randInt(int min, int max) {
		return RANDOM.nextInt(max - min + 1) + min;
	}
}

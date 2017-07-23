package com.blakebr0.cucumber.util;

import java.text.NumberFormat;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.lwjgl.input.Keyboard;

import net.minecraft.util.text.TextComponentTranslation;

public class Utils {
	
	public static Random rand = new Random();
	
	public static String localize(String string){
		return new TextComponentTranslation(string).getFormattedText();
	}
	
	/**
	 * Adds commas to the specified numerical value
	 * @param obj the number
	 * @return the formatted number
	 */
	public static String format(Object obj){
		return NumberFormat.getInstance().format(obj);
	}
	
	public static boolean isShiftKeyDown(){
        return Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT);
    }
	
	public static List<String> asList(String string){
		return Collections.singletonList(string);
	}
	
	public static int randInt(int min, int max){
		return rand.nextInt(max - min + 1) + min;
	}
}

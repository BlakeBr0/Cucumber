package com.blakebr0.cucumber.registry;

import java.util.ArrayList;

import com.blakebr0.cucumber.guide.Guide;

public class GuideRegistry {

	private static ArrayList<Guide> guides = new ArrayList<>();
	
	public static void register(Guide guide) {
		if (!guides.contains(guide)) {
			guides.add(guide);
		}
	}
	
	public static Guide getGuide(String name) {
		for (Guide g : guides) {
			if (g.getName() == name) {
				return g;
			}
		}
		return null;
	}
}

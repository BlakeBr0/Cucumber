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
		return guides.stream().filter(g -> g.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
	}
	
	public static ArrayList<Guide> getGuides() {
		return guides;
	}
}

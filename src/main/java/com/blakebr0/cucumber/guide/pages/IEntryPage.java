package com.blakebr0.cucumber.guide.pages;

import java.util.Map;

import com.blakebr0.cucumber.lib.Colors;
import com.blakebr0.cucumber.util.Utils;

import net.minecraft.client.Minecraft;

public interface IEntryPage {

	void draw(Minecraft mc, int mouseX, int mouseY, float partialTicks, int x, int y, int width, int height);
	
	public static String formatText(String key) {
		return formatText(key, null);
	}
	
	public static String formatText(String key, Map<String, String> textReplacements) {
		String s = Utils.localize(key);
		
		s = s.replaceAll("<b>", Colors.BOLD)
			 .replaceAll("<i>", Colors.ITALICS)
			 .replaceAll("<u>", Colors.UNDERLINE)
			 .replaceAll("<r>", Colors.RESET)
			 .replaceAll("<n>", "\n")
			 .replaceAll("<red>", Colors.RED)
			 .replaceAll("<green>", Colors.GREEN)
			 .replaceAll("<blue>", Colors.BLUE);
		
		if (textReplacements != null && !textReplacements.isEmpty()) {
			for (Map.Entry<String, String> r : textReplacements.entrySet()) {
				s = s.replaceAll(r.getKey(), r.getValue());
			}
		}
		
		return s;
	}
}

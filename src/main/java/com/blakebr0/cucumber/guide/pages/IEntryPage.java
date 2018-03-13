package com.blakebr0.cucumber.guide.pages;

import com.blakebr0.cucumber.lib.Colors;
import com.blakebr0.cucumber.util.Utils;

import net.minecraft.client.Minecraft;

public interface IEntryPage {

	void draw(Minecraft mc, int mouseX, int mouseY, float partialTicks, int x, int y, int width, int height);
	
	public static String formatText(String key) {
		String s = Utils.localize(key);
		
		s = s.replaceAll("<b>", Colors.BOLD)
			 .replaceAll("<i>", Colors.ITALICS)
			 .replaceAll("<u>", Colors.UNDERLINE)
			 .replaceAll("<r>", Colors.RESET);
		
		return s;
	}
}

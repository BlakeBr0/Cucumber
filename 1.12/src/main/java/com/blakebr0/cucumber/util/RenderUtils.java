package com.blakebr0.cucumber.util;

import com.blakebr0.cucumber.render.GlowingTextRenderer;
import com.blakebr0.cucumber.render.GlowingTextRenderer.ColorInfo;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

public class RenderUtils {
	
	/*
	 * Credit to elucent for embers https://github.com/RootsTeam/Embers/blob/master/src/main/java/teamroots/embers/gui/GuiCodex.java
	 */
	public static void drawGlowingText(FontRenderer font, String s, int x, int y, ColorInfo info) {
		float sine = 0.5F * ((float) Math.sin(Math.toRadians(4.0F * ((float) GlowingTextRenderer.getTicks() + Minecraft.getMinecraft().getRenderPartialTicks()))) + 1.0F);
		font.drawStringWithShadow(s, x, y, Utils.intColor(info.r + (int) (info.rl * sine), info.g + (int) (info.gl * sine), info.b + (int) (info.bl * sine)));
	}
}

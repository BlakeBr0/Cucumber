package com.blakebr0.cucumber.helper;

import java.util.List;

import com.blakebr0.cucumber.render.GlowingTextRenderer;
import com.blakebr0.cucumber.render.GlowingTextRenderer.ColorInfo;
import com.blakebr0.cucumber.util.Utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;

public class RenderHelper {
	
	/*
	 * Credit to elucent for embers https://github.com/RootsTeam/Embers/blob/master/src/main/java/teamroots/embers/gui/GuiCodex.java
	 */
	public static void drawGlowingText(FontRenderer font, String s, int x, int y, ColorInfo info) {
		float sine = 0.5F * ((float) Math.sin(Math.toRadians(4.0F * ((float) GlowingTextRenderer.getTicks() + Minecraft.getMinecraft().getRenderPartialTicks()))) + 1.0F);
		font.drawStringWithShadow(s, x, y, Utils.intColor(info.r + (int) (info.rl * sine), info.g + (int) (info.gl * sine), info.b + (int) (info.bl * sine)));
	}
	
	/*
	 * Credit to ellpeck for actually additions https://github.com/Ellpeck/ActuallyAdditions/blob/6ded1cc7b37e240642847a3addf90f5273844666/src/main/java/de/ellpeck/actuallyadditions/mod/util/StringUtil.java
	 */
	public static void drawScaledWrappedText(FontRenderer font, String s, int x, int y, float scale, float width, float height, int color, boolean shadow) {
        GlStateManager.pushMatrix();
        GlStateManager.scale(scale, scale, scale);
        boolean oldUnicode = font.getUnicodeFlag();
        font.setUnicodeFlag(false);
        List<String> lines = font.listFormattedStringToWidth(s, (int) (width / scale));
        for (int i = 0; i < lines.size() && (height > -1 ? i * font.FONT_HEIGHT < height : true); i++) {
        	font.drawString(lines.get(i), x / scale, y / scale + (i * (int) (font.FONT_HEIGHT * scale)), color, shadow);        
        }
        font.setUnicodeFlag(oldUnicode);
        GlStateManager.popMatrix();
	}
	
	public static void drawScaledWrappedText(FontRenderer font, String s, int x, int y, float scale, float width, int color, boolean shadow) {
		drawScaledWrappedText(font, s, x, y, scale, width, -1, color, shadow);
	}
}

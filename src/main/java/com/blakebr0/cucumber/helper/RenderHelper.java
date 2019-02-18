package com.blakebr0.cucumber.helper;

import com.blakebr0.cucumber.render.GlowingTextRenderer;
import com.blakebr0.cucumber.render.GlowingTextRenderer.ColorInfo;
import com.blakebr0.cucumber.util.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;

import java.util.List;

public class RenderHelper {

    public static void drawCenteredText(FontRenderer font, String s, int x, int y, int color) {
        font.drawString(s, (x - font.getStringWidth(s) / 2), y, color);
    }
	
	/*
	 * Credit to ellpeck for actually additions https://github.com/Ellpeck/ActuallyAdditions/blob/6ded1cc7b37e240642847a3addf90f5273844666/src/main/java/de/ellpeck/actuallyadditions/mod/util/StringUtil.java
	 */
	public static void drawScaledWrappedText(FontRenderer font, String s, int x, int y, float scale, float width, float height, int color, boolean shadow) {
        GlStateManager.pushMatrix();
        GlStateManager.scalef(scale, scale, scale);
        List<String> lines = font.listFormattedStringToWidth(s, (int) (width / scale));
        for (int i = 0; i < lines.size() && (height > -1 ? i * font.FONT_HEIGHT < height : true); i++) {
        	if (shadow) {
				font.drawStringWithShadow(lines.get(i), x / scale, y / scale + (i * (int) (font.FONT_HEIGHT * scale)), color);
			} else {
				font.drawString(lines.get(i), x / scale, y / scale + (i * (int) (font.FONT_HEIGHT * scale)), color);
			}
        }
        GlStateManager.popMatrix();
	}
	
	public static void drawScaledWrappedText(FontRenderer font, String s, int x, int y, float scale, float width, int color, boolean shadow) {
		drawScaledWrappedText(font, s, x, y, scale, width, -1, color, shadow);
	}
	
	public static void drawScaledCenteredWrappedTextX(FontRenderer font, String s, int x, int y, float scale, float width, float height, int color, boolean shadow) {
        GlStateManager.pushMatrix();
        GlStateManager.scalef(scale, scale, scale);
        List<String> lines = font.listFormattedStringToWidth(s, (int) (width / scale));
        for (int i = 0; i < lines.size() && (height > -1 ? i * font.FONT_HEIGHT < height : true); i++) {
        	if (shadow) {
				font.drawStringWithShadow(lines.get(i), (x - font.getStringWidth(lines.get(i)) / 2) / scale, y / scale + (i * (int) (font.FONT_HEIGHT * scale)), color);
			} else {
				font.drawString(lines.get(i), (x - font.getStringWidth(lines.get(i)) / 2) / scale, y / scale + (i * (int) (font.FONT_HEIGHT * scale)), color);
			}
        }
        GlStateManager.popMatrix();	
	}
	
	public static void drawScaledCenteredWrappedTextY(FontRenderer font, String s, int x, int y, float scale, float width, float height, int color, boolean shadow) {
        List<String> lines = font.listFormattedStringToWidth(s, (int) (width / scale));
        y -= (font.FONT_HEIGHT / 2 / scale) * (lines.size() - 1);
        drawScaledWrappedText(font, s, x, y, scale, width, height, color, shadow);
	}
	
	public static void drawScaledCenteredWrappedTextXY(FontRenderer font, String s, int x, int y, float scale, float width, float height, int color, boolean shadow) {
        List<String> lines = font.listFormattedStringToWidth(s, (int) (width / scale));
        y -= (font.FONT_HEIGHT / 2 / scale) * (lines.size() - 1);
		drawScaledCenteredWrappedTextX(font, s, x, y, scale, width, height, color, shadow);
	}
	
	public static void drawTexturedModalRect(int x, int y, int textureX, int textureY, int width, int height) {
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
		bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
		bufferbuilder.pos((double) (x + 0), (double) (y + height), (double) 0)
					 .tex((double) ((float) (textureX + 0) * 0.00390625F), (double) ((float) (textureY + height) * 0.00390625F))
					 .endVertex();
		bufferbuilder.pos((double) (x + width), (double) (y + height), (double) 0)
					 .tex((double) ((float) (textureX + width) * 0.00390625F), (double) ((float) (textureY + height) * 0.00390625F))
					 .endVertex();
		bufferbuilder.pos((double) (x + width), (double) (y + 0), (double) 0)
					 .tex((double) ((float) (textureX + width) * 0.00390625F), (double) ((float) (textureY + 0) * 0.00390625F))
					 .endVertex();
		bufferbuilder.pos((double) (x + 0), (double) (y + 0), (double) 0)
					 .tex((double) ((float) (textureX + 0) * 0.00390625F), (double) ((float) (textureY + 0) * 0.00390625F))
					 .endVertex();
		tessellator.draw();
	}
	
    public static void drawTexturedModalRect(double x, double y, double u, double v, double width, double height, double textureWidth, double textureHeight) {
        double f = 1.0D / textureWidth;
        double f1 = 1.0D / textureHeight;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos(x, (y + height), 0)
        			 .tex((u * f), ((v + height) * f1))
        			 .endVertex();
        bufferbuilder.pos((x + width), (y + height), 0)
        			 .tex(((u + width) * f), ((v + height) * f1))
        			 .endVertex();
        bufferbuilder.pos((x + width), y, 0)
        			 .tex(((u + width) * f), (v * f1))
        			 .endVertex();
        bufferbuilder.pos(x, y, 0)
        			 .tex((u * f), (v * f1))
        			 .endVertex();
        tessellator.draw();
    }
    
    public static void drawScaledItemIntoGui(ItemRenderer render, ItemStack stack, int x, int y, float scale) {
    	GlStateManager.pushMatrix();
    	GlStateManager.scalef(scale, scale, scale);
    	net.minecraft.client.renderer.RenderHelper.enableGUIStandardItemLighting();
    	render.renderItemAndEffectIntoGUI(stack, (int) (x / scale), (int) (y / scale));
    	net.minecraft.client.renderer.RenderHelper.disableStandardItemLighting();
    	GlStateManager.popMatrix();
    }
}

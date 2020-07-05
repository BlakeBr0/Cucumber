package com.blakebr0.cucumber.client.helper;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextProperties;

// TODO: 1.16: reevaluate
public final class RenderHelper {
	public static void drawCenteredText(FontRenderer font, MatrixStack stack, String text, int x, int y, int color) {
		font.func_238405_a_(stack, text, (x - font.getStringWidth(text) / 2F), y, color);
	}

	/*
	 * Credit to ellpeck for actually additions:
	 * https://github.com/Ellpeck/ActuallyAdditions/blob/6ded1cc7b37e240642847a3addf90f5273844666/src/main/java/de/ellpeck/actuallyadditions/mod/util/StringUtil.java
	 */
	public static void drawScaledWrappedText(FontRenderer font, MatrixStack stack, String s, int x, int y, float scale, float width, float height, int color, boolean shadow) {
//		stack.push();
//		stack.scale(scale, scale, scale);
//		List<String> lines = font.listFormattedStringToWidth(s, (int) (width / scale));
//		for (int i = 0; i < lines.size() && (!(height > -1) || i * font.FONT_HEIGHT < height); i++) {
//			if (shadow) {
//				font.drawStringWithShadow(lines.get(i), x / scale, y / scale + (i * (int) (font.FONT_HEIGHT * scale)), color);
//			} else {
//				font.drawString(lines.get(i), x / scale, y / scale + (i * (int) (font.FONT_HEIGHT * scale)), color);
//			}
//		}
//
//		stack.pop();
	}

	public static void drawScaledWrappedText(FontRenderer font, MatrixStack stack, String s, int x, int y, float scale, float width, int color, boolean shadow) {
		drawScaledWrappedText(font, stack, s, x, y, scale, width, -1, color, shadow);
	}

	public static void drawScaledCenteredWrappedTextX(FontRenderer font, MatrixStack stack, ITextProperties text, int x, int y, float scale, float width, float height, int color, boolean shadow) {
//		stack.push();
//		stack.scale(scale, scale, scale);
//		List<ITextProperties> lines = font.func_238425_b_(text, (int) (width / scale));
//		for (int i = 0; i < lines.size() && (!(height > -1) || i * font.FONT_HEIGHT < height); i++) {
//			if (shadow) {
//				font.drawStringWithShadow(lines.get(i), (x - font.getStringWidth(lines.get(i).getString()) / 2) / scale, y / scale + (i * (int) (font.FONT_HEIGHT * scale)), color);
//			} else {
//				font.drawString(lines.get(i), (x - font.getStringWidth(lines.get(i).getString()) / 2) / scale, y / scale + (i * (int) (font.FONT_HEIGHT * scale)), color);
//			}
//		}
//
//		stack.pop();
	}

	public static void drawScaledCenteredWrappedTextY(FontRenderer font, String s, int x, int y, float scale, float width, float height, int color, boolean shadow) {
//		List<String> lines = font.listFormattedStringToWidth(s, (int) (width / scale));
//		y -= (font.FONT_HEIGHT / 2 / scale) * (lines.size() - 1);
//		drawScaledWrappedText(font, s, x, y, scale, width, height, color, shadow);
	}

	public static void drawScaledCenteredWrappedTextXY(FontRenderer font, String s, int x, int y, float scale, float width, float height, int color, boolean shadow) {
//		List<String> lines = font.listFormattedStringToWidth(s, (int) (width / scale));
//		y -= (font.FONT_HEIGHT / 2 / scale) * (lines.size() - 1);
//		drawScaledCenteredWrappedTextX(font, s, x, y, scale, width, height, color, shadow);
	}

	public static void drawTexturedModalRect(int x, int y, int textureX, int textureY, int width, int height) {
		drawTexturedModalRect(x, y, textureX, textureY, width, height, 256, 256);
	}

	public static void drawTexturedModalRect(double x, double y, double u, double v, double width, double height, double textureWidth, double textureHeight) {
		double f = 1.0D / textureWidth;
		double f1 = 1.0D / textureHeight;
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder buffer = tessellator.getBuffer();
		buffer.begin(7, DefaultVertexFormats.POSITION_TEX);
		buffer.pos(x, (y + height), 0)
				.tex((float) (u * f), (float) ((v + height) * f1))
				.endVertex();
		buffer.pos(x + width, y + height, 0)
				.tex((float) ((u + width) * f), (float) ((v + height) * f1))
				.endVertex();
		buffer.pos(x + width, y, 0)
				.tex((float) ((u + width) * f), (float) (v * f1))
				.endVertex();
		buffer.pos(x, y, 0)
				.tex((float) (u * f), (float) (v * f1))
				.endVertex();
		tessellator.draw();
	}

	public static void drawScaledItemIntoGui(ItemRenderer render, ItemStack stack, int x, int y, float scale) {
		RenderSystem.pushMatrix();
		RenderSystem.scalef(scale, scale, scale);
		net.minecraft.client.renderer.RenderHelper.enableStandardItemLighting();
		render.renderItemAndEffectIntoGUI(stack, (int) (x / scale), (int) (y / scale));
		net.minecraft.client.renderer.RenderHelper.disableStandardItemLighting();
		RenderSystem.popMatrix();
	}
}

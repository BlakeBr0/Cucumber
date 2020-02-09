package com.blakebr0.cucumber.gui;

import com.blakebr0.cucumber.Cucumber;
import com.blakebr0.cucumber.helper.RenderHelper;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class GuiIcons {
	public static final ResourceLocation ICONS = new ResourceLocation(Cucumber.MOD_ID, "textures/gui/icons.png");
	
	public static void drawX(int x, int y) {
		Minecraft.getInstance().getTextureManager().bindTexture(ICONS);
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		RenderHelper.drawTexturedModalRect(x, y, 0, 0, 13, 13);
	}
	
	public static void drawCheck(int x, int y) {
		Minecraft.getInstance().getTextureManager().bindTexture(ICONS);
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		RenderHelper.drawTexturedModalRect(x, y, 0, 15, 13, 12);
	}
	
	public static void drawCheckOrX(int x, int y, boolean valid) {
		if (valid) {
			drawCheck(x, y);
		} else {
			drawX(x, y);
		}
	}
}

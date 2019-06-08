package com.blakebr0.cucumber.gui;

import com.blakebr0.cucumber.Cucumber;
import com.blakebr0.cucumber.helper.RenderHelper;
import com.blakebr0.cucumber.helper.ResourceHelper;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class GuiIcons {
	public static final ResourceLocation ICONS = ResourceHelper.getResource(Cucumber.MOD_ID, "textures/gui/icons.png");
	
	public static void drawX(int x, int y) {
		Minecraft.getInstance().getTextureManager().bindTexture(ICONS);
		GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		RenderHelper.drawTexturedModalRect(x, y, 0, 0, 13, 13);
	}
	
	public static void drawCheck(int x, int y) {
		Minecraft.getInstance().getTextureManager().bindTexture(ICONS);
		GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
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

package com.blakebr0.cucumber.guide.pages;

import com.blakebr0.cucumber.helper.RenderHelper;

import net.minecraft.client.Minecraft;

public class PageText implements IEntryPage {

	public String key;
	
	public PageText(String key) {
		this.key = key;
	}

	@Override
	public void draw(Minecraft mc, int mouseX, int mouseY, float partialTicks, int x, int y, int width, int height) {
		RenderHelper.drawScaledWrappedText(mc.fontRenderer, IEntryPage.formatText(this.key), x, y, 1.0F, width, height, 0, false);
	}
}

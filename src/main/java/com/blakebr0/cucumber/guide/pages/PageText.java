package com.blakebr0.cucumber.guide.pages;

import com.blakebr0.cucumber.helper.RenderHelper;

import net.minecraft.client.Minecraft;

public class PageText implements IEntryPage {

	public String key;
	public String text;
	
	public PageText(String key) {
		this.key = key;
		this.text = IEntryPage.formatText(key);
	}

	@Override
	public void draw(Minecraft mc, int mouseX, int mouseY, float partialTicks, int x, int y, int width, int height) {
		RenderHelper.drawScaledWrappedText(mc.fontRenderer, this.text, x, y, 1.0F, width, 130 - height, 0, false);
	}
}

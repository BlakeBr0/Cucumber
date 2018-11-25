package com.blakebr0.cucumber.guide.pages;

import java.util.Map;

import com.blakebr0.cucumber.helper.RenderHelper;

import net.minecraft.client.Minecraft;

public class PageText implements IEntryPage {

	public String key;
	public Map<String, String> textReplacements;
	
	public PageText(String key, Map<String, String> textReplacements) {
		this.key = key;
		this.textReplacements = textReplacements;
	}

	@Override
	public void draw(Minecraft mc, int mouseX, int mouseY, float partialTicks, int x, int y, int width, int height) {
		RenderHelper.drawScaledWrappedText(mc.fontRenderer, IEntryPage.formatText(this.key, this.textReplacements), x, y, 1.0F, width, height, 0, false);
	}
}

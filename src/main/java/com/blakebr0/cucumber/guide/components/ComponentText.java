package com.blakebr0.cucumber.guide.components;

import java.util.List;

import com.blakebr0.cucumber.helper.RenderHelper;
import com.blakebr0.cucumber.util.Utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ComponentText implements IEntryComponent {

	public String key;
	public String loc;
	
	public ComponentText(String key) {
		this.key = key;
		this.loc = Utils.localize(key);
	}

	@Override
	public void draw(int mouseX, int mouseY, float partialTicks, int x, int y, int width, int height, int offset, int page) {
		RenderHelper.drawScaledWrappedText(Minecraft.getMinecraft().fontRenderer, this.loc, x, y, 1.0F, width, 130 - height, 0, false);
	}

	@Override
	public int height() {
		return Minecraft.getMinecraft().fontRenderer.listFormattedStringToWidth(this.loc, 160).size() * Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT;
	}
}

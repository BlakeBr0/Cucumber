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
	
	public ComponentText(String key) {
		this.key = key;
	}

	@Override
	public void draw(int mouseX, int mouseY, float partialTicks, int x, int y, int width, int height, int page) {
		RenderHelper.drawScaledWrappedText(Minecraft.getMinecraft().fontRenderer, Utils.localize(key), x, y, 1.0F, width, 150 - height, 0, false);
	}

	@Override
	public int height() {
		return Minecraft.getMinecraft().fontRenderer.listFormattedStringToWidth(Utils.localize(key), 160).size() * Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT;
	}
}

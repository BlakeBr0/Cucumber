package com.blakebr0.cucumber.client.screen.button;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class IconButtonStatic extends Button {
	private final ResourceLocation texture;
	private final int textureX, textureY;

	public IconButtonStatic(int x, int y, int width, int height, int textureX, int textureY, ResourceLocation texture, OnPress onPress) {
		this(x, y, width, height, textureX, textureY, Component.literal(""), texture, onPress);
	}

	public IconButtonStatic(int x, int y, int width, int height, int textureX, int textureY, ResourceLocation texture, OnPress onPress, CreateNarration onTooltip) {
		this(x, y, width, height, textureX, textureY, Component.literal(""), texture, onPress, onTooltip);
	}

	public IconButtonStatic(int x, int y, int width, int height, int textureX, int textureY, Component text, ResourceLocation texture, OnPress onPress) {
		this(x, y, width, height, textureX, textureY, text, texture, onPress, DEFAULT_NARRATION);
	}

	public IconButtonStatic(int x, int y, int width, int height, int textureX, int textureY, Component text, ResourceLocation texture, OnPress onPress, CreateNarration onTooltip) {
		super(x, y, width, height, text, onPress, onTooltip);
		this.textureX = textureX;
		this.textureY = textureY;
		this.texture = texture;
	}
	
	@Override
	public void renderWidget(GuiGraphics gfx, int mouseX, int mouseY, float partialTicks) {
		gfx.blit(this.texture, this.getX(), this.getY(), this.textureX, this.textureY, this.width, this.height);
	}
}

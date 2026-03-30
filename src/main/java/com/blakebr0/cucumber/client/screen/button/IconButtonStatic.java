package com.blakebr0.cucumber.client.screen.button;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

public class IconButtonStatic extends Button {
	private final Identifier texture;
	private final int textureX, textureY;

	public IconButtonStatic(int x, int y, int width, int height, int textureX, int textureY, Identifier texture) {
		this(x, y, width, height, textureX, textureY, Component.literal(""), texture);
	}

	public IconButtonStatic(int x, int y, int width, int height, int textureX, int textureY, Identifier texture, CreateNarration onTooltip) {
		this(x, y, width, height, textureX, textureY, Component.literal(""), texture, onTooltip);
	}

	public IconButtonStatic(int x, int y, int width, int height, int textureX, int textureY, Component text, Identifier texture) {
		this(x, y, width, height, textureX, textureY, text, texture, DEFAULT_NARRATION);
	}

	public IconButtonStatic(int x, int y, int width, int height, int textureX, int textureY, Component text, Identifier texture, CreateNarration onTooltip) {
		super(x, y, width, height, text, _ -> {}, onTooltip);
		this.textureX = textureX;
		this.textureY = textureY;
		this.texture = texture;
	}
	
	@Override
	public void extractContents(GuiGraphicsExtractor gfx, int mouseX, int mouseY, float partialTicks) {
		gfx.blit(RenderPipelines.GUI_TEXTURED, this.texture, this.getX(), this.getY(), this.textureX, this.textureY, this.width, this.height, 256, 256);
	}
}

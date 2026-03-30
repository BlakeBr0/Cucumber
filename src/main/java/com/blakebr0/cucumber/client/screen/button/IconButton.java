package com.blakebr0.cucumber.client.screen.button;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

public class IconButton extends Button {
	private final Identifier texture;
	private final float textureX, textureY;
	private final OnTooltip tooltip;

	public IconButton(int x, int y, int width, int height, float textureX, float textureY, Identifier texture) {
		this(x, y, width, height, textureX, textureY, Component.literal(""), texture);
	}

	public IconButton(int x, int y, int width, int height, float textureX, float textureY, Identifier texture, OnTooltip onTooltip) {
		this(x, y, width, height, textureX, textureY, Component.literal(""), texture, onTooltip);
	}

	public IconButton(int x, int y, int width, int height, float textureX, float textureY, Component text, Identifier texture) {
		this(x, y, width, height, textureX, textureY, text, texture, null);
	}

	public IconButton(int x, int y, int width, int height, float textureX, float textureY, Component text, Identifier texture, OnTooltip onTooltip) {
		super(x, y, width, height, text, _ -> {}, DEFAULT_NARRATION);
		this.textureX = textureX;
		this.textureY = textureY;
		this.texture = texture;
		this.tooltip = onTooltip;
	}
	
	@Override
	public void extractContents(GuiGraphicsExtractor gfx, int mouseX, int mouseY, float partialTicks) {
		int i = this.getYImage();

		gfx.blit(RenderPipelines.GUI_TEXTURED, this.texture, this.getX(), this.getY(), this.textureX, this.textureY + i * this.height, this.width, this.height, 256, 256);

		if (this.tooltip != null && this.isHovered()) {
			this.tooltip.extract(this, gfx, mouseX, mouseY);
		}
	}

	protected int getYImage() {
		int i = 1;
		if (!this.active) {
			i = 0;
		} else if (this.isHoveredOrFocused()) {
			i = 2;
		}

		return i;
	}

	@FunctionalInterface
	public interface OnTooltip {
		void extract(Button button, GuiGraphicsExtractor gfx, int mouseX, int mouseY);
	}
}

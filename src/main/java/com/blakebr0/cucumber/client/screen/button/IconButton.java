package com.blakebr0.cucumber.client.screen.button;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class IconButton extends Button {
	private final ResourceLocation texture;
	private final int textureX, textureY;
	private final OnTooltip tooltip;

	public IconButton(int x, int y, int width, int height, int textureX, int textureY, ResourceLocation texture, OnPress onPress) {
		this(x, y, width, height, textureX, textureY, Component.literal(""), texture, onPress);
	}

	public IconButton(int x, int y, int width, int height, int textureX, int textureY, ResourceLocation texture, OnPress onPress, OnTooltip onTooltip) {
		this(x, y, width, height, textureX, textureY, Component.literal(""), texture, onPress, onTooltip);
	}

	public IconButton(int x, int y, int width, int height, int textureX, int textureY, Component text, ResourceLocation texture, OnPress onPress) {
		this(x, y, width, height, textureX, textureY, text, texture, onPress, null);
	}

	public IconButton(int x, int y, int width, int height, int textureX, int textureY, Component text, ResourceLocation texture, OnPress onPress, OnTooltip onTooltip) {
		super(x, y, width, height, text, onPress, DEFAULT_NARRATION);
		this.textureX = textureX;
		this.textureY = textureY;
		this.texture = texture;
		this.tooltip = onTooltip;
	}
	
	@Override
	public void renderWidget(PoseStack matrix, int mouseX, int mouseY, float partialTicks) {
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		RenderSystem.setShaderTexture(0, this.texture);
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		RenderSystem.enableDepthTest();

		int i = this.getYImage();

		blit(matrix, this.getX(), this.getY(), this.textureX, this.textureY + i * this.height, this.width, this.height);

		if (this.tooltip != null && this.isHoveredOrFocused()) {
			this.tooltip.render(this, matrix, mouseX, mouseY);
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
		void render(Button button, PoseStack matrix, int mouseX, int mouseY);
	}
}

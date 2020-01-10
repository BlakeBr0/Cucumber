package com.blakebr0.cucumber.gui.button;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.AbstractButton;
import net.minecraft.util.ResourceLocation;

public class IconButton extends AbstractButton {
	private ResourceLocation texture;
	private int textureX, textureY;

	public IconButton(int x, int y, int width, int height, int textureX, int textureY, String text, ResourceLocation texture) {
		super(x, y, width, height, text);
		this.textureX = textureX;
		this.textureY = textureY;
		this.texture = texture;
	}
	
	@Override
	public void renderButton(int mouseX, int mouseY, float partialTicks) {
		Minecraft minecraft = Minecraft.getInstance();
		minecraft.getTextureManager().bindTexture(this.texture);
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, this.alpha);
		int i = this.getYImage(this.isHovered());
		RenderSystem.enableBlend();
		RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
		RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
		this.blit(this.x, this.y, this.textureX, this.textureY + i * 20, this.width / 2, this.height);
	}

	@Override
	public void onPress() {

	}
}

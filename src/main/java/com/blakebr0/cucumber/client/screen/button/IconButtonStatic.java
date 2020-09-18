package com.blakebr0.cucumber.client.screen.button;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class IconButtonStatic extends Button {
	private final ResourceLocation texture;
	private final int textureX, textureY;

	public IconButtonStatic(int x, int y, int width, int height, int textureX, int textureY, ResourceLocation texture, IPressable onPress) {
		this(x, y, width, height, textureX, textureY, new StringTextComponent(""), texture, onPress);
	}

	public IconButtonStatic(int x, int y, int width, int height, int textureX, int textureY, ITextComponent text, ResourceLocation texture, IPressable onPress) {
		super(x, y, width, height, text, onPress);
		this.textureX = textureX;
		this.textureY = textureY;
		this.texture = texture;
	}
	
	@Override
	public void renderButton(MatrixStack stack, int mouseX, int mouseY, float partialTicks) {
		Minecraft minecraft = Minecraft.getInstance();
		minecraft.getTextureManager().bindTexture(this.texture);
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, this.alpha);
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		RenderSystem.enableDepthTest();
		this.blit(stack, this.x, this.y, this.textureX, this.textureY, this.width, this.height);

		if (this.isHovered()) {
			super.renderToolTip(stack, mouseX, mouseY);
		}
	}
}

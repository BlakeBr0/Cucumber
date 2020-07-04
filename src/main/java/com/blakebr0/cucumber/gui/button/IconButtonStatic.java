package com.blakebr0.cucumber.gui.button;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.AbstractButton;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class IconButtonStatic extends AbstractButton {
	private final ResourceLocation texture;
	private final int textureX, textureY;

	public IconButtonStatic(int x, int y, int width, int height, int textureX, int textureY, ITextComponent text, ResourceLocation texture) {
		super(x, y, width, height, text);
		this.textureX = textureX;
		this.textureY = textureY;
		this.texture = texture;
	}
	
	@Override
	public void func_230430_a_(MatrixStack stack, int mouseX, int mouseY, float partialTicks) {
//		if (this.visible) {
//			Minecraft mc = Minecraft.getInstance();
//			mc.getTextureManager().bindTexture(this.texture);
//			RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
//			RenderSystem.enableBlend();
//			RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
//			RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
//			this.blit(stack, this.x, this.y, this.textureX, this.textureY, this.width, this.height);
//		}
	}

	@Override
	public void func_230930_b_() {

	}
}

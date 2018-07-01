package com.blakebr0.cucumber.gui.button;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class IconButtonStatic extends GuiButton {
	
	private ResourceLocation texture;
	private int textureX, textureY;

	public IconButtonStatic(int id, int x, int y, int width, int height, int textureX, int textureY, String text, ResourceLocation texture) {
		super(id, x, y, width, height, text);
		this.textureX = textureX;
		this.textureY = textureY;
		this.texture = texture;
	}
	
	@Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
        if (this.visible) {
            mc.getTextureManager().bindTexture(this.texture);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
            int i = this.getHoverState(this.hovered);
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            this.drawTexturedModalRect(this.x, this.y, this.textureX, this.textureY, this.width, this.height);
            this.mouseDragged(mc, mouseX, mouseY);
        }
    }
}

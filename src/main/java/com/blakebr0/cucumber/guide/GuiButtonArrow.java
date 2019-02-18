package com.blakebr0.cucumber.guide;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;

public class GuiButtonArrow extends GuiButton {

	private boolean flip;
	
	public GuiButtonArrow(int id, int x, int y, boolean flip) {
		super(id, x, y, 29, 15, "");
		this.flip = flip;
	}

	@Override
	public void render(int mouseX, int mouseY, float partialTicks) {
        if (this.visible) {
            Minecraft mc = Minecraft.getInstance();
            mc.getTextureManager().bindTexture(GuiGuide.WIDGET_TEX);
            GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
            int i = this.getHoverState(this.hovered);
            int j = this.flip ? this.width : 0;
            this.drawTexturedModalRect(this.x, this.y, j, 49 + (i * 15), this.width, this.height);
        }
	}
}

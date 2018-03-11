package com.blakebr0.cucumber.guide;

import com.blakebr0.cucumber.util.Utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;

public class GuiButtonArrow extends GuiButton {

	private int color;
	private boolean flip;
	
	public GuiButtonArrow(int id, int x, int y, int color, boolean flip) {
		super(id, x, y, 29, 15, "");
		this.color = color;
		this.flip = flip;
	}

	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
        if (this.visible) {
            mc.getTextureManager().bindTexture(GuiGuide.WIDGET_TEX);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
            int i = this.getHoverState(this.hovered);
            int j = this.flip ? this.width : 0;
            this.drawTexturedModalRect(this.x, this.y, j, 60 + (i * 15), this.width, this.height);
            this.mouseDragged(mc, mouseX, mouseY);
        }
	}
}

package com.blakebr0.cucumber.guide;

import com.blakebr0.cucumber.helper.RenderHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;

public class GuiButtonEntry extends GuiButton {
	
	private ItemStack stack;
	
	public GuiButtonEntry(int id, int x, int y, int width, int height, String text, ItemStack stack) {
		super(id, x, y, width, height, text);
		this.stack = stack;
	}

	@Override
	public void render(int mouseX, int mouseY, float partialTicks) {
        if (this.visible) {
            Minecraft mc = Minecraft.getInstance();
            FontRenderer font = mc.fontRenderer;
            mc.getTextureManager().bindTexture(GuiGuide.WIDGET_TEX);
            GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
            int i = this.getHoverState(this.hovered);
            GlStateManager.enableBlend();
            GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            this.drawTexturedModalRect(this.x, this.y, 0, i * 16, this.width / 2, this.height);
            this.drawTexturedModalRect(this.x + this.width / 2, this.y, 200 - this.width / 2, i * 16, this.width / 2, this.height);

            int j = 14737632;
            if (this.packedFGColor != 0) {
                j = this.packedFGColor;
            } else if (!this.enabled) {
                j = 10526880;
            } else if (this.hovered) {
                j = 16777120;
            }
            
            if (font.getStringWidth(this.displayString) > this.width) {
            	this.displayString = font.trimStringToWidth(this.displayString, this.width - 30) + "...";
            }
            
            RenderHelper.drawScaledItemIntoGui(mc.getItemRenderer(), this.stack, this.x + 3, this.y + 2, 0.785F);
            this.drawString(font, this.displayString, this.x + 20, this.y + (this.height - 8) / 2, j);
        }
	}
}

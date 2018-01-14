package com.blakebr0.cucumber.guide;

import java.io.IOException;

import com.blakebr0.cucumber.guide.components.IEntryComponent;
import com.blakebr0.cucumber.helper.RenderHelper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;

public class GuiEntry extends GuiScreen {

	public static final int COMPONENT_SPACING = 6;
	
	public GuiGuide parent;
	public GuideEntry entry;
	public int xSize, ySize;
	
	public GuiEntry(GuiGuide parent, GuideEntry entry) {
		this.parent = parent;
		this.entry = entry;
		this.xSize = parent.xSize;
		this.ySize = parent.ySize;
	}
	
	@Override
	public void initGui() {
		super.initGui();
		this.buttonList.add(new GuiButton(100, (this.height - this.ySize) / 2 + 20, (this.height - this.ySize) / 2 + ySize, "fuck go back"));
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(parent.GUI_TEX);
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);
		RenderHelper.drawScaledWrappedText(fontRenderer, entry.getTitle(), (this.width - this.xSize) / 2 + 23, (this.height - this.ySize) / 2 + 10, 1.3F, 160, 0, false);
		int drawAt = (this.height - this.ySize) / 2 + 30;
		int height = 0;
		for (IEntryComponent comp : entry.getComponents()) {
			if (drawAt + comp.height() > this.height) {
				//break;
			}
			comp.draw(mouseX, mouseY, partialTicks, x + 20, drawAt, 160, height, 1);
			drawAt += comp.height() + COMPONENT_SPACING;
			height += comp.height();
		}
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		if (button.id == 100) {
			Minecraft.getMinecraft().displayGuiScreen(parent);
		}
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
}

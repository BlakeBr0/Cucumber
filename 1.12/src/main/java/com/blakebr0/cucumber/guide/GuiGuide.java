package com.blakebr0.cucumber.guide;

import com.blakebr0.cucumber.Cucumber;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class GuiGuide extends GuiScreen {

	public static final ResourceLocation GUI_TEX = new ResourceLocation(Cucumber.MOD_ID, "textures/gui/guide.png");
	public ItemStack book;
	public Guide guide;
	public int xSize, ySize;
	
	public GuiGuide(ItemStack book, Guide guide) {
		this.book = book;
		this.guide = guide;
		this.xSize = 200;
		this.ySize = 223;
	}
	
	@Override
	public void initGui() {
		super.initGui();
		int offset = 0;
		int i = 0;
		for (GuideEntry e : guide.getEntries()) {
			this.buttonList.add(new GuiButton(i++, (this.width - this.xSize) / 2, (this.height - this.ySize) / 2, 160, 20, "test"));
		}
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(GUI_TEX);
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
}

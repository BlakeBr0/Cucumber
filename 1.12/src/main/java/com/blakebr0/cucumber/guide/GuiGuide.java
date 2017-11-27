package com.blakebr0.cucumber.guide;

import java.io.IOException;

import com.blakebr0.cucumber.Cucumber;
import com.blakebr0.cucumber.network.NetworkHandler;
import com.blakebr0.cucumber.network.messages.MessageUpdateGuideNBT;
import com.blakebr0.cucumber.util.RenderUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class GuiGuide extends GuiScreen {

	public static final ResourceLocation GUI_TEX = new ResourceLocation(Cucumber.MOD_ID, "textures/gui/guide.png");
	public static final int ENTRIES_PER_PAGE = 8;
	public ItemStack book;
	public Guide guide;
	public int xSize, ySize;
	public int page;
	
	public GuiGuide(ItemStack book, Guide guide) {
		this.book = book;
		this.guide = guide;
		this.xSize = 200;
		this.ySize = 223;
	}
	
	@Override
	public void initGui() {
		super.initGui();
		this.page = book.getTagCompound().getInteger("Page");
		int offset = 0;
		int i = ENTRIES_PER_PAGE * page;
		for (int x = 0; x < ENTRIES_PER_PAGE && i < guide.getEntries().size(); x++) {
			this.buttonList.add(new GuiButtonEntry(i, (this.width - this.xSize) / 2 + 20, (this.height - this.ySize) / 2 + 40 + (x * 18), 160, 14, "test"));
			i++;
		}
		this.buttonList.add(new GuiButton(10000, (this.width - this.xSize) / 2 + 9, (this.height - this.ySize) / 2 + 200, 30, 12, "<<<"));
		this.buttonList.add(new GuiButton(10001, (this.width - this.xSize) / 2 + 160, (this.height - this.ySize) / 2 + 200, 30, 12, ">>>"));
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(GUI_TEX);
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);
		RenderUtils.drawScaledWrappedText(fontRenderer, "Select a category", (this.width - this.xSize) / 2 + 23, (this.height - this.ySize) / 2 + 10, 1.5F, 160, 0, false);
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		if (button.id == 10000) {
			book.getTagCompound().setInteger("Page", book.getTagCompound().getInteger("Page") - 1);
			this.page--;
			Minecraft.getMinecraft().displayGuiScreen(new GuiGuide(book, guide));
		} else if (button.id == 10001) {
			book.getTagCompound().setInteger("Page", book.getTagCompound().getInteger("Page") + 1);
			this.page++;
			Minecraft.getMinecraft().displayGuiScreen(new GuiGuide(book, guide));
		}
		
		if (button instanceof GuiButtonEntry) {
			this.guide.getEntries().get(button.id).open(this);
		}
	}
	
	@Override
	public void onGuiClosed() {
		super.onGuiClosed();
		NetworkHandler.NETWORK.sendToServer(new MessageUpdateGuideNBT(page));
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
}

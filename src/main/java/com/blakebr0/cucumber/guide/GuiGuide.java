package com.blakebr0.cucumber.guide;

import java.io.IOException;

import com.blakebr0.cucumber.Cucumber;
import com.blakebr0.cucumber.guide.pages.IEntryPage;
import com.blakebr0.cucumber.helper.RenderHelper;
import com.blakebr0.cucumber.helper.ResourceHelper;
import com.blakebr0.cucumber.lib.Colors;
import com.blakebr0.cucumber.network.NetworkHandler;
import com.blakebr0.cucumber.network.messages.MessageUpdateGuideNBT;
import com.blakebr0.cucumber.util.Utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiGuide extends GuiScreen {

	public static final ResourceLocation GUI_TEX = ResourceHelper.getResource(Cucumber.MOD_ID, "textures/gui/guide.png");
	public static final ResourceLocation WIDGET_TEX = ResourceHelper.getResource(Cucumber.MOD_ID, "textures/gui/guide_widgets.png");
	public static final int ENTRIES_PER_PAGE = 9;
	public static final int COMPONENT_SPACING = 6;
	public ItemStack book;
	public Guide guide;
	public int xSize, ySize;
	public int xStart, yStart;
	public int topicsPage, maxTopicsPage;
	public int entryPage, maxEntryPage;
	public GuideEntry entry;
	public int entryId;
	public GuiButtonArrow prevEntry, nextEntry, prevPage, nextPage;
	
	public GuiGuide(ItemStack book, Guide guide) {
		this.book = book;
		this.guide = guide;
		this.xSize = 436;
		this.ySize = 240;
		
		this.topicsPage = GuideBookHelper.getTopicsPage(this.book);
		this.maxTopicsPage = (this.guide.getEntryCount() / ENTRIES_PER_PAGE) - 1;
		this.entryId = GuideBookHelper.getEntryId(this.book);
		this.entry = this.entryId > -1 ? this.guide.getEntryById(this.entryId) : this.guide.hasEntries() ? this.guide.getEntryById(0) : null;
		this.entryPage = GuideBookHelper.getEntryPage(this.book);
		this.maxEntryPage = this.entry != null ? this.entry.getPageCount() - 2 : -1;
	}
	
	@Override
	public void initGui() {
		super.initGui();
		this.xStart = (this.width - this.xSize) / 2;
		this.yStart = (this.height - this.ySize) / 2;
		
		this.prevEntry = this.addButton(new GuiButtonArrow(10000, this.xStart + 17, this.yStart + 207, true));
		this.nextEntry = this.addButton(new GuiButtonArrow(10001, this.xStart + 145, this.yStart + 207, false));
		this.prevPage = this.addButton(new GuiButtonArrow(10002, this.xStart + 196, this.yStart + 207, true));
		this.nextPage = this.addButton(new GuiButtonArrow(10003, this.xStart + 390, this.yStart + 207, false));
		
		this.updateEntryButtons();
		this.updatePageButtons();
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(GUI_TEX);
		RenderHelper.drawTexturedModelRect(this.xStart, this.yStart, 0, 0, this.xSize, this.ySize, 512, 512);
		
		RenderHelper.drawScaledCenteredWrappedTextXY(this.fontRenderer, Colors.BOLD + this.guide.getName(), this.xStart + 95, this.yStart + 22, 1.0F, 120, -1, 0, false);
		
		if (this.entry != null) {
			RenderHelper.drawScaledCenteredWrappedTextY(this.fontRenderer, Colors.BOLD + this.entry.getTitle(), this.xStart + 227, this.yStart + 25, 1.0F, 190, -1, 0, false);
			RenderHelper.drawScaledItemIntoGui(this.itemRender, this.entry.getIconStack(), this.xStart + 197, this.yStart + 18, 1.54F);
			if (this.entry.hasPages()) {
				this.entry.getPage(this.entryPage).draw(this.mc, mouseX, mouseY, partialTicks, this.xStart + 200, this.yStart + 63, 220, 130);
			}
		}
		
		RenderHelper.drawCenteredText(this.fontRenderer, Utils.localize("guide.cu.page_indicator", this.topicsPage + 1, this.maxTopicsPage + 2), this.xStart + 96, this.yStart + 211, 0);
		RenderHelper.drawCenteredText(this.fontRenderer, Utils.localize("guide.cu.page_indicator", this.entryPage + 1, Math.max(this.maxEntryPage + 2, 1)), this.xStart + 308, this.yStart + 211, 0);

		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		if (button instanceof GuiButtonEntry) {
			this.entry = this.guide.getEntryById(button.id);
			this.entryId = button.id;
			this.entryPage = 0;
			this.maxEntryPage = this.entry.getPageCount() - 2;
			this.updateEntryButtons();
			this.updatePageButtons();
		} else if (button == this.prevEntry && this.topicsPage > 0) {
			this.topicsPage -= 1;
			this.updateEntryButtons();
		} else if (button == this.nextEntry && this.topicsPage <= this.maxTopicsPage) {
			this.topicsPage++;
			this.updateEntryButtons();
		} else if (button == this.prevPage) {
			this.entryPage--;
			this.updatePageButtons();
		} else if (button == this.nextPage && this.entryPage <= this.maxEntryPage) {
			this.entryPage++;
			this.updatePageButtons();
		}
	}
	
	@Override
	public void onGuiClosed() {
		NetworkHandler.INSTANCE.sendToServer(new MessageUpdateGuideNBT(this.topicsPage, this.entryPage, this.entryId));
		super.onGuiClosed();
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
	
	public void updateEntryButtons() {
		this.buttonList.removeIf(b -> b.id < 10000);
		
		this.prevEntry.enabled = this.topicsPage > 0;
		this.nextEntry.enabled = this.topicsPage <= this.maxTopicsPage;
		
		int i = ENTRIES_PER_PAGE * this.topicsPage;
		for (int x = 0; x < ENTRIES_PER_PAGE && i < this.guide.getEntryCount(); x++) {
			GuideEntry entry = this.guide.getEntryById(i);
			GuiButtonEntry button = new GuiButtonEntry(i, this.xStart + 20, this.yStart + 41 + (x * 18), 151, 16, entry.getTitle(), entry.getIconStack());
			
			if (i == this.entryId) {
				button.enabled = false;
			}
			
			this.addButton(button);
			
			i++;
		}
	}
	
	public void updatePageButtons() {
		this.prevPage.enabled = this.entryPage > 0;
		this.nextPage.enabled = this.entryPage <= this.maxEntryPage;
	}
}

package com.blakebr0.cucumber.guide;

import java.io.IOException;

import com.blakebr0.cucumber.Cucumber;
import com.blakebr0.cucumber.guide.components.IEntryComponent;
import com.blakebr0.cucumber.helper.RenderHelper;
import com.blakebr0.cucumber.network.NetworkHandler;
import com.blakebr0.cucumber.network.messages.MessageUpdateGuideNBT;
import com.blakebr0.cucumber.util.Utils;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiGuide extends GuiScreen {

	public static final ResourceLocation GUI_TEX = new ResourceLocation(Cucumber.MOD_ID, "textures/gui/guide.png");
	public static final ResourceLocation WIDGET_TEX = new ResourceLocation(Cucumber.MOD_ID, "textures/gui/guide_widgets.png");
	public static final int ENTRIES_PER_PAGE = 8;
	public static final int COMPONENT_SPACING = 6;
	public ItemStack book;
	public Guide guide;
	public int xSize, ySize;
	public int xStart, yStart;
	public int page, maxPage, page1;
	public GuideEntry entry;
	public GuiButtonArrow prevEntry, nextEntry, prevPage, nextPage;
	
	public GuiGuide(ItemStack book, Guide guide) {
		this.book = book;
		this.guide = guide;
		this.xSize = 436;
		this.ySize = 240;
	}
	
	@Override
	public void initGui() {
		super.initGui();
		this.xStart = (this.width - this.xSize) / 2;
		this.yStart = (this.height - this.ySize) / 2;
		this.page = GuideBookHelper.getEntryPage(this.book);
		this.maxPage = (this.guide.getEntries().size() / ENTRIES_PER_PAGE) - 1;
		this.page1 = 1;
		int offset = 0;
		
		this.prevEntry = this.addButton(new GuiButtonArrow(10000, this.xStart + 17, this.yStart + 207, true));
		this.nextEntry = this.addButton(new GuiButtonArrow(10001, this.xStart + 145, this.yStart + 207, false));
		this.prevPage = this.addButton(new GuiButtonArrow(10002, this.xStart + 196, this.yStart + 207,true));
		this.nextPage = this.addButton(new GuiButtonArrow(10003, this.xStart + 390, this.yStart + 207, false));
		
		this.updateEntryButtons();
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(GUI_TEX);
		int x = this.xStart;
		int y = this.yStart;
		RenderHelper.drawTexturedModelRect(x, y, 0, 0, this.xSize, this.ySize, 512, 512);
		
		if (entry != null) {
			RenderHelper.drawScaledWrappedText(fontRenderer, entry.getTitle(), (this.width - this.xSize) / 2 + 235, (this.height - this.ySize) / 2 + 16, 1.3F, 160, 0, false);
			int drawAt = this.yStart + 60;
			int height = 0;
			for (IEntryComponent comp : entry.getComponents()) {
				if (drawAt + comp.height() > this.height) {
					//break;
				}
				comp.draw(mouseX, mouseY, partialTicks, x + 200, drawAt, 220, height, page1 * comp.height(), page1);
				drawAt += comp.height() + COMPONENT_SPACING;
				height += comp.height();
			}
		}
		
		super.drawScreen(mouseX, mouseY, partialTicks);
		
		RenderHelper.drawCenteredText(this.fontRenderer, Utils.localize("guide.cu.page_indicator", this.page + 1, this.maxPage + 2), x + 96, this.yStart + 211, 0);
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		if (button instanceof GuiButtonEntry) {
			button.enabled = false;
			entry = guide.getEntries().get(button.id);
		}
		
		if (button == this.prevEntry && this.page > 0) {
			book.getTagCompound().setInteger("Page", book.getTagCompound().getInteger("Page") - 1);
			this.page -= 1;
			this.updateEntryButtons();
		} else if (button == this.nextEntry && this.page <= this.maxPage) {
			book.getTagCompound().setInteger("Page", book.getTagCompound().getInteger("Page") + 1);
			this.page++;
			this.updateEntryButtons();
		} else if (button == this.prevPage) {
			this.page1--;
		} else if (button == this.nextPage) {
			this.page1++;
		}
	}
	
	@Override
	public void onGuiClosed() {
		super.onGuiClosed();
		NetworkHandler.INSTANCE.sendToServer(new MessageUpdateGuideNBT(page));
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
	
	public void updateEntryButtons() {		
		this.buttonList.removeIf(b -> b.id < 10000);
		
		this.prevEntry.enabled = this.page > 0;
		this.nextEntry.enabled = this.page <= this.maxPage;
		
		int i = ENTRIES_PER_PAGE * this.page;
		for (int x = 0; x < ENTRIES_PER_PAGE && i < guide.getEntries().size(); x++) {
			this.addButton(new GuiButtonEntry(i, this.xStart + 20, this.yStart + 40 + (x * 20), 138, 20, guide.getEntries().get(i).getTitle(), this.guide.getColor()));
			i++;
		}
	}
}

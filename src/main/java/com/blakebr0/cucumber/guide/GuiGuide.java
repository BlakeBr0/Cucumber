package com.blakebr0.cucumber.guide;

import java.io.IOException;

import com.blakebr0.cucumber.Cucumber;
import com.blakebr0.cucumber.guide.components.IEntryComponent;
import com.blakebr0.cucumber.helper.RenderHelper;
import com.blakebr0.cucumber.network.NetworkHandler;
import com.blakebr0.cucumber.network.messages.MessageUpdateGuideNBT;

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

	public static final ResourceLocation GUI_TEX = new ResourceLocation(Cucumber.MOD_ID, "textures/gui/guide.png");
	public static final ResourceLocation WIDGET_TEX = new ResourceLocation(Cucumber.MOD_ID, "textures/gui/guide_widgets.png");
	public static final int ENTRIES_PER_PAGE = 8;
	public static final int COMPONENT_SPACING = 6;
	public ItemStack book;
	public Guide guide;
	public int xSize, ySize;
	public int page;
	public boolean save = true;
	public String wew = "monkaS";
	public GuideEntry entry;
	
	public GuiGuide(ItemStack book, Guide guide) {
		this.book = book;
		this.guide = guide;
		this.xSize = 436;
		this.ySize = 240;
	}
	
	@Override
	public void initGui() {
		super.initGui();
		this.page = book.getTagCompound().getInteger("Page");
		int offset = 0;
		int i = ENTRIES_PER_PAGE * page;
		for (int x = 0; x < ENTRIES_PER_PAGE && i < guide.getEntries().size(); x++) {
			this.buttonList.add(new GuiButtonEntry(i, (this.width - this.xSize) / 2 + 20, (this.height - this.ySize) / 2 + 40 + (x * 20), 138, 20, guide.getEntries().get(x).getTitle()));
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
		RenderHelper.drawTexturedModelRect(x, y, 0, 0, this.xSize, this.ySize, 512, 512);
		RenderHelper.drawScaledWrappedText(fontRenderer, wew, (this.width - this.xSize) / 2 + 23, (this.height - this.ySize) / 2 + 10, 1.5F, 160, 0, false);
		
		if (entry != null) {
			RenderHelper.drawScaledWrappedText(fontRenderer, entry.getTitle(), (this.width - this.xSize) / 2 + 235, (this.height - this.ySize) / 2 + 16, 1.3F, 160, 0, false);
			int drawAt = (this.height - this.ySize) / 2 + 60;
			int height = 0;
			for (IEntryComponent comp : entry.getComponents()) {
				if (drawAt + comp.height() > this.height) {
					//break;
				}
				comp.draw(mouseX, mouseY, partialTicks, x + 200, drawAt, 220, height, 1);
				drawAt += comp.height() + COMPONENT_SPACING;
				height += comp.height();
			}
		}

		
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	@SuppressWarnings("unused")
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		
		if (button instanceof GuiButtonEntry) {
			wew = guide.getEntries().get(button.id).getTitle();
			button.enabled = false;
			entry = guide.getEntries().get(button.id);
		}
		
		if (false) {
			if (button.id == 10000 && this.page > 0) {
				book.getTagCompound().setInteger("Page", book.getTagCompound().getInteger("Page") - 1);
				this.page--;
				this.save = false;
				Minecraft.getMinecraft().displayGuiScreen(new GuiGuide(book, guide));
			} else if (button.id == 10001) {
				book.getTagCompound().setInteger("Page", book.getTagCompound().getInteger("Page") + 1);
				this.page++;
				this.save = false;
				Minecraft.getMinecraft().displayGuiScreen(new GuiGuide(book, guide));
			}
			
			if (button instanceof GuiButtonEntry) {
				// TODO: save later
				this.guide.getEntries().get(button.id).open(this);
			}
		}
	}
	
	@Override
	public void onGuiClosed() {
		super.onGuiClosed();
		if (save) {
			NetworkHandler.INSTANCE.sendToServer(new MessageUpdateGuideNBT(page));
			this.save = false;
		}
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
}

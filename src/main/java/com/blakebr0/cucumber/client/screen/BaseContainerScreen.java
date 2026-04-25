package com.blakebr0.cucumber.client.screen;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;

import java.text.NumberFormat;

public abstract class BaseContainerScreen<T extends AbstractContainerMenu> extends AbstractContainerScreen<T> {
    protected Identifier bgTexture;
    protected int bgWidth;
    protected int bgHeight;
    protected int bgImgWidth;
    protected int bgImgHeight;

    public BaseContainerScreen(T container, Inventory inventory, Component title, Identifier bgTexture, int bgWidth, int bgHeight) {
        this(container, inventory, title, bgTexture, bgWidth, bgHeight, 256, 256);
    }

    public BaseContainerScreen(T container, Inventory inventory, Component title, Identifier bgTexture, int bgWidth, int bgHeight, int bgImgWidth, int bgImgHeight) {
        super(container, inventory, title, bgWidth, bgHeight);
        this.bgTexture = bgTexture;
        this.bgWidth = bgWidth;
        this.bgHeight = bgHeight;
        this.bgImgWidth = bgImgWidth;
        this.bgImgHeight = bgImgHeight;
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor gfx, int mouseX, int mouseY, float a) {
        super.extractBackground(gfx, mouseX, mouseY, a);

        int x = this.getGuiLeft();
        int y = this.getGuiTop();

        gfx.blit(RenderPipelines.GUI_TEXTURED, this.bgTexture, x, y, 0, 0, this.bgWidth, this.bgHeight, this.bgImgWidth, this.bgImgHeight);
    }

    protected static boolean isHoveringSlot(int x, int y, int mouseX, int mouseY) {
        return mouseX > x - 1 && mouseX < x + 16 && mouseY > y - 1 && mouseY < y + 16;
    }

    protected static void extractSlotHighlightBack(GuiGraphicsExtractor gfx, int x, int y) {
        gfx.blitSprite(RenderPipelines.GUI_TEXTURED, SLOT_HIGHLIGHT_BACK_SPRITE, x, y, 24, 24);
    }

    protected static void extractSlotHighlightFront(GuiGraphicsExtractor gfx, int x, int y) {
        gfx.blitSprite(RenderPipelines.GUI_TEXTURED, SLOT_HIGHLIGHT_FRONT_SPRITE, x, y, 24, 24);
    }

    protected static String text(String key, Object... args) {
        return Component.translatable(key, args).getString();
    }

    protected static String number(Object number) {
        return NumberFormat.getInstance().format(number);
    }
}

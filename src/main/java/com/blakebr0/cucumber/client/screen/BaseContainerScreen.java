package com.blakebr0.cucumber.client.screen;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;

import java.text.NumberFormat;

public abstract class BaseContainerScreen<T extends AbstractContainerMenu> extends AbstractContainerScreen<T> {
    protected Identifier bgTexture;
    protected int bgImgWidth;
    protected int bgImgHeight;

    public BaseContainerScreen(T container, Inventory inventory, Component title, Identifier bgTexture, int bgWidth, int bgHeight) {
        this(container, inventory, title, bgTexture, bgWidth, bgHeight, 256, 256);
    }

    public BaseContainerScreen(T container, Inventory inventory, Component title, Identifier bgTexture, int bgWidth, int bgHeight, int bgImgWidth, int bgImgHeight) {
        super(container, inventory, title);
        this.bgTexture = bgTexture;
        this.bgImgWidth = bgImgWidth;
        this.bgImgHeight = bgImgHeight;
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor gfx, int mouseX, int mouseY, float a) {
        int x = this.getGuiLeft();
        int y = this.getGuiTop();

        gfx.blit(this.bgTexture, x, y, 0, 0, this.bgImgWidth, this.bgImgHeight, 256, 256);
    }

    protected static String text(String key, Object... args) {
        return Component.translatable(key, args).getString();
    }

    protected static String number(Object number) {
        return NumberFormat.getInstance().format(number);
    }
}

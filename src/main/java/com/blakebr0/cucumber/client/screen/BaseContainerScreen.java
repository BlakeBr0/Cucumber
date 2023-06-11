package com.blakebr0.cucumber.client.screen;

import com.blakebr0.cucumber.util.Localizable;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;

import java.text.NumberFormat;

public abstract class BaseContainerScreen<T extends AbstractContainerMenu> extends AbstractContainerScreen<T> {
    protected ResourceLocation bgTexture;
    protected int bgImgWidth;
    protected int bgImgHeight;

    public BaseContainerScreen(T container, Inventory inventory, Component title, ResourceLocation bgTexture, int bgWidth, int bgHeight) {
        this(container, inventory, title, bgTexture, bgWidth, bgHeight, 256, 256);
    }

    public BaseContainerScreen(T container, Inventory inventory, Component title, ResourceLocation bgTexture, int bgWidth, int bgHeight, int bgImgWidth, int bgImgHeight) {
        super(container, inventory, title);
        this.imageWidth = bgWidth;
        this.imageHeight = bgHeight;
        this.bgTexture = bgTexture;
        this.bgImgWidth = bgImgWidth;
        this.bgImgHeight = bgImgHeight;
    }

    @Override
    public void render(GuiGraphics gfx, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(gfx);
        super.render(gfx, mouseX, mouseY, partialTicks);
        this.renderTooltip(gfx, mouseX, mouseY);
    }

    protected void renderDefaultBg(GuiGraphics gfx, float partialTicks, int mouseX, int mouseY) {
        int x = this.getGuiLeft();
        int y = this.getGuiTop();

        gfx.blit(this.bgTexture, x, y, 0, 0, this.imageWidth, this.imageHeight, this.bgImgWidth, this.bgImgHeight);
    }

    protected static String text(String key, Object... args) {
        return Localizable.of(key).args(args).buildString();
    }

    protected static String number(Object number) {
        return NumberFormat.getInstance().format(number);
    }
}

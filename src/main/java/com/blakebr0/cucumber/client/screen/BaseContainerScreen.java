package com.blakebr0.cucumber.client.screen;

import com.blakebr0.cucumber.util.Localizable;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

import java.text.NumberFormat;

public class BaseContainerScreen<T extends Container> extends ContainerScreen<T> {
    protected ResourceLocation bgTexture;
    protected int bgImgWidth;
    protected int bgImgHeight;

    public BaseContainerScreen(T container, PlayerInventory inventory, ITextComponent title, ResourceLocation bgTexture, int bgWidth, int bgHeight) {
        this(container, inventory, title, bgTexture, bgWidth, bgHeight, 256, 256);
    }

    public BaseContainerScreen(T container, PlayerInventory inventory, ITextComponent title, ResourceLocation bgTexture, int bgWidth, int bgHeight, int bgImgWidth, int bgImgHeight) {
        super(container, inventory, title);
        this.bgTexture = bgTexture;
        this.imageWidth = bgWidth;
        this.imageHeight = bgHeight;
        this.bgImgWidth = bgImgWidth;
        this.bgImgHeight = bgImgHeight;
    }

    @Override
    public void render(MatrixStack stack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(stack);
        super.render(stack, mouseX, mouseY, partialTicks);
        this.renderTooltip(stack, mouseX, mouseY);
    }

    @Override
    protected void renderBg(MatrixStack stack, float partialTicks, int mouseX, int mouseY) {
        this.getMinecraft().getTextureManager().bind(this.bgTexture);
        int x = this.getGuiLeft();
        int y = this.getGuiTop();

        blit(stack, x, y, 0, 0, this.imageWidth, this.imageHeight, this.bgImgWidth, this.bgImgHeight);
    }

    protected static String text(String key, Object... args) {
        return Localizable.of(key).args(args).buildString();
    }

    protected static String number(Object number) {
        return NumberFormat.getInstance().format(number);
    }
}

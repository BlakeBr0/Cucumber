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
    protected int bgWidth;
    protected int bgHeight;

    public BaseContainerScreen(T container, PlayerInventory inventory, ITextComponent title, ResourceLocation bgTexture, int xSize, int ySize) {
        this(container, inventory, title, bgTexture, xSize, ySize, 256, 256);
    }

    public BaseContainerScreen(T container, PlayerInventory inventory, ITextComponent title, ResourceLocation bgTexture, int xSize, int ySize, int bgWidth, int bgHeight) {
        super(container, inventory, title);
        this.xSize = xSize;
        this.ySize = ySize;
        this.bgTexture = bgTexture;
        this.bgWidth = bgWidth;
        this.bgHeight = bgHeight;
    }

    @Override
    public void render(MatrixStack stack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(stack);
        super.render(stack, mouseX, mouseY, partialTicks);
        this.renderHoveredTooltip(stack, mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack stack, float partialTicks, int mouseX, int mouseY) {
        this.getMinecraft().getTextureManager().bindTexture(this.bgTexture);
        int x = this.getGuiLeft();
        int y = this.getGuiTop();

        blit(stack, x, y, 0, 0, this.xSize, this.ySize, this.bgWidth, this.bgHeight);
    }

    protected static String text(String key, Object... args) {
        return Localizable.of(key).args(args).buildString();
    }

    protected static String number(Object number) {
        return NumberFormat.getInstance().format(number);
    }
}

package com.blakebr0.cucumber.client.screen;

import com.blakebr0.cucumber.util.Localizable;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;

import java.text.NumberFormat;

public class BaseContainerScreen<T extends AbstractContainerMenu> extends AbstractContainerScreen<T> {
    protected ResourceLocation bgTexture;
    protected int bgWidth;
    protected int bgHeight;

    public BaseContainerScreen(T container, Inventory inventory, Component title, ResourceLocation bgTexture, int width, int height) {
        this(container, inventory, title, bgTexture, width, height, 256, 256);
    }

    public BaseContainerScreen(T container, Inventory inventory, Component title, ResourceLocation bgTexture, int width, int height, int bgWidth, int bgHeight) {
        super(container, inventory, title);
        this.width = width;
        this.height = height;
        this.bgTexture = bgTexture;
        this.bgWidth = bgWidth;
        this.bgHeight = bgHeight;
    }

    @Override
    public void render(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(stack);
        super.render(stack, mouseX, mouseY, partialTicks);
        this.renderLabels(stack, mouseX, mouseY);
    }

    @Override
    protected void renderBg(PoseStack stack, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, this.bgTexture);

        int x = this.getGuiLeft();
        int y = this.getGuiTop();

        blit(stack, x, y, 0, 0, this.width, this.height, this.bgWidth, this.bgHeight);
    }

    protected static String text(String key, Object... args) {
        return Localizable.of(key).args(args).buildString();
    }

    protected static String number(Object number) {
        return NumberFormat.getInstance().format(number);
    }
}

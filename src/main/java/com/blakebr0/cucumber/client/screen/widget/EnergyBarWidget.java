package com.blakebr0.cucumber.client.screen.widget;

import com.blakebr0.cucumber.Cucumber;
import com.blakebr0.cucumber.util.Formatting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.energy.IEnergyStorage;

public class EnergyBarWidget extends AbstractWidget {
    private static final ResourceLocation WIDGETS_TEXTURE = new ResourceLocation(Cucumber.MOD_ID, "textures/gui/widgets.png");
    private final IEnergyStorage energy;
    private final AbstractContainerScreen<?> screen;

    public EnergyBarWidget(int x, int y, IEnergyStorage energy, AbstractContainerScreen<?> screen) {
        super(x, y, 14, 78, Component.literal("Energy Bar"));
        this.energy = energy;
        this.screen = screen;
    }

    @Override
    public void render(PoseStack matrix, int mouseX, int mouseY, float partialTicks) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, WIDGETS_TEXTURE);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, this.alpha);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();

        int offset = this.getEnergyBarOffset();

        this.blit(matrix, this.getX(), this.getY(), 0, 0, this.width, this.height);
        this.blit(matrix, this.getX(), this.getY() + this.height - offset, 14, this.height - offset, this.width,  offset + 1);

        if (mouseX >= this.getX() && mouseY >= this.getY() && mouseX < this.getX() + this.width && mouseY < this.getY() + this.height) {
            var text = Component.literal(Formatting.number(this.energy.getEnergyStored()).getString() + " / " + Formatting.energy(this.energy.getMaxEnergyStored()).getString());

            this.screen.renderTooltip(matrix, text, mouseX, mouseY);
        }
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narration) { }

    private int getEnergyBarOffset() {
        int i = this.energy.getEnergyStored();
        int j = this.energy.getMaxEnergyStored();
        return (int) (j != 0 && i != 0 ? i * (long) this.height / j : 0);
    }
}

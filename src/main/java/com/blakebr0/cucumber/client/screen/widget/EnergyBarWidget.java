package com.blakebr0.cucumber.client.screen.widget;

import com.blakebr0.cucumber.Cucumber;
import com.blakebr0.cucumber.util.Formatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.energy.IEnergyStorage;

public class EnergyBarWidget extends AbstractWidget {
    private static final ResourceLocation WIDGETS_TEXTURE = new ResourceLocation(Cucumber.MOD_ID, "textures/gui/widgets.png");
    private final IEnergyStorage energy;

    public EnergyBarWidget(int x, int y, IEnergyStorage energy) {
        super(x, y, 14, 78, Component.literal("Energy Bar"));
        this.energy = energy;
    }

    @Override
    public void render(GuiGraphics gfx, int mouseX, int mouseY, float partialTicks) {
        int offset = this.getEnergyBarOffset();

        gfx.blit(WIDGETS_TEXTURE, this.getX(), this.getY(), 0, 0, this.width, this.height);
        gfx.blit(WIDGETS_TEXTURE, this.getX(), this.getY() + this.height - offset, 14, this.height - offset, this.width,  offset + 1);

        if (mouseX >= this.getX() && mouseY >= this.getY() && mouseX < this.getX() + this.width && mouseY < this.getY() + this.height) {
            var font = Minecraft.getInstance().font;
            var text = Component.literal(Formatting.number(this.energy.getEnergyStored()).getString() + " / " + Formatting.energy(this.energy.getMaxEnergyStored()).getString());

            gfx.renderTooltip(font, text, mouseX, mouseY);
        }
    }

    @Override
    public void renderWidget(GuiGraphics matrix, int mouseX, int mouseY, float partialTicks) { }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narration) { }

    private int getEnergyBarOffset() {
        int i = this.energy.getEnergyStored();
        int j = this.energy.getMaxEnergyStored();
        return (int) (j != 0 && i != 0 ? i * (long) this.height / j : 0);
    }
}

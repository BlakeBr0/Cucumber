package com.blakebr0.cucumber.client.screen.widget;

import com.blakebr0.cucumber.Cucumber;
import com.blakebr0.cucumber.util.Formatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.energy.IEnergyStorage;

import java.util.function.IntSupplier;

public class EnergyBarWidget extends AbstractWidget {
    private static final ResourceLocation WIDGETS_TEXTURE = Cucumber.resource("textures/gui/widgets.png");

    private final IntSupplier energy;
    private final IntSupplier capacity;

    @Deprecated(forRemoval = true)
    public EnergyBarWidget(int x, int y, IEnergyStorage energy) {
        this(x, y, energy::getEnergyStored, energy::getMaxEnergyStored);
    }

    public EnergyBarWidget(int x, int y, IntSupplier energy, IntSupplier capacity) {
        super(x, y, 14, 78, Component.literal("Energy Bar"));
        this.energy = energy;
        this.capacity = capacity;
    }

    @Override
    public void renderWidget(GuiGraphics gfx, int mouseX, int mouseY, float partialTicks) {
        int offset = this.getEnergyBarOffset();

        gfx.blit(WIDGETS_TEXTURE, this.getX(), this.getY(), 0, 0, this.width, this.height);
        gfx.blit(WIDGETS_TEXTURE, this.getX(), this.getY() + this.height - offset, 14, this.height - offset, this.width,  offset + 1);

        if (mouseX >= this.getX() && mouseY >= this.getY() && mouseX < this.getX() + this.width && mouseY < this.getY() + this.height) {
            var font = Minecraft.getInstance().font;
            var text = Formatting.number(this.energy.getAsInt()).append(" / ").append(Formatting.energy(this.capacity.getAsInt()));

            gfx.renderTooltip(font, text, mouseX, mouseY);
        }
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narration) { }

    private int getEnergyBarOffset() {
        int i = this.energy.getAsInt();
        int j = this.capacity.getAsInt();
        return (int) (j != 0 && i != 0 ? i * (long) this.height / j : 0);
    }
}

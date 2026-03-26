package com.blakebr0.cucumber.client.screen.widget;

import com.blakebr0.cucumber.Cucumber;
import com.blakebr0.cucumber.util.Formatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

import java.util.function.IntSupplier;

public class FuelWidget extends AbstractWidget {
    private static final Identifier WIDGETS_TEXTURE = Cucumber.resource("textures/gui/widgets.png");

    private final IntSupplier fuelValue;
    private final IntSupplier fuelLeft;

    public FuelWidget(int x, int y, IntSupplier fuelValue, IntSupplier fuelLeft) {
        super(x, y, 14, 14, Component.literal("Fuel"));
        this.fuelValue = fuelValue;
        this.fuelLeft = fuelLeft;
        this.active = false; // not a clickable element
    }

    @Override
    protected void extractWidgetRenderState(GuiGraphicsExtractor gfx, int mouseX, int mouseY, float partialTicks) {
        int offset = this.getBurnLeftScaled();

        gfx.blit(RenderPipelines.GUI_TEXTURED, WIDGETS_TEXTURE, this.getX(), this.getY(), 30, 0, this.width, this.height, 256, 256);
        gfx.blit(RenderPipelines.GUI_TEXTURED, WIDGETS_TEXTURE, this.getX(), this.getY() + this.height - offset, 44, this.height - offset, this.width,  offset + 1, 256, 256);

        if (mouseX >= this.getX() && mouseY >= this.getY() && mouseX < this.getX() + this.width && mouseY < this.getY() + this.height) {
            var font = Minecraft.getInstance().font;
            var text = Formatting.energy(this.fuelLeft.getAsInt());

            gfx.setTooltipForNextFrame(font, text, mouseX, mouseY);
        }
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput output) { }

    private int getBurnLeftScaled() {
        int i = this.fuelLeft.getAsInt();
        int j = this.fuelValue.getAsInt();
        return (int) (j != 0 && i != 0 ? (long) i * this.height / j : 0);
    }
}

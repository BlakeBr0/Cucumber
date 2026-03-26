package com.blakebr0.cucumber.client.screen.widget;

import com.blakebr0.cucumber.Cucumber;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

import java.util.function.IntSupplier;

public class ProgressArrowWidget extends AbstractWidget {
    private static final Identifier WIDGETS_TEXTURE = Cucumber.resource("textures/gui/widgets.png");

    private final IntSupplier progress;
    private final IntSupplier total;

    public ProgressArrowWidget(int x, int y, IntSupplier progress, IntSupplier total) {
        super(x, y, 24, 16, Component.literal("Progress Arrow"));
        this.progress = progress;
        this.total = total;
        this.active = false; // not a clickable element
    }

    @Override
    protected void extractWidgetRenderState(GuiGraphicsExtractor gfx, int mouseX, int mouseY, float partialTicks) {
        int offset = this.getProgressScaled();

        gfx.blit(RenderPipelines.GUI_TEXTURED, WIDGETS_TEXTURE, this.getX(), this.getY(), 30, 16, this.width, this.height, 256, 256);
        gfx.blit(RenderPipelines.GUI_TEXTURED, WIDGETS_TEXTURE, this.getX(), this.getY(), 30, 16 + this.height, offset + 1, this.height, 256, 256);
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput output) { }

    private int getProgressScaled() {
        int i = this.progress.getAsInt();
        int j = this.total.getAsInt();
        return j != 0 && i != 0 ? i * this.width / j : 0;
    }
}

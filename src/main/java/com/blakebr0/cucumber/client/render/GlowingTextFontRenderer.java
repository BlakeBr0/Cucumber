package com.blakebr0.cucumber.client.render;

import com.blakebr0.cucumber.helper.ColorHelper;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;

// TODO: 1.17 reevalute
public class GlowingTextFontRenderer extends Font {
    private final GlowingTextRenderer.ColorInfo info;

    public GlowingTextFontRenderer(Font parent, GlowingTextRenderer.ColorInfo info) {
        super(parent.fonts);
        this.info = info;
    }

    @Override
    public int draw(PoseStack stack, String text, float x, float y, int color) {
        float sine = 0.5F * ((float) Math.sin(Math.toRadians(4.0F * ((float) GlowingTextRenderer.getTicks() + Minecraft.getInstance().getFrameTime()))) + 1.0F);
        return super.draw(stack, text, x, y, ColorHelper.intColor(this.info.r + (int) (this.info.rl * sine), this.info.g + (int) (this.info.gl * sine), this.info.b + (int) (this.info.bl * sine)));
    }

    @Override
    public int drawShadow(PoseStack stack, String text, float x, float y, int color) {
        float sine = 0.5F * ((float) Math.sin(Math.toRadians(4.0F * ((float) GlowingTextRenderer.getTicks() + Minecraft.getInstance().getFrameTime()))) + 1.0F);
        return super.drawShadow(stack, text, x, y, ColorHelper.intColor(this.info.r + (int) (this.info.rl * sine), this.info.g + (int) (this.info.gl * sine), this.info.b + (int) (this.info.bl * sine)));
    }
}

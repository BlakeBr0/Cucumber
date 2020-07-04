package com.blakebr0.cucumber.client.render;

import com.blakebr0.cucumber.util.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

// TODO: 1.16: reevaluate
//public class GlowingTextFontRenderer extends FontRenderer {
//    private final GlowingTextRenderer.ColorInfo info;
//
//    public GlowingTextFontRenderer(FontRenderer parent, GlowingTextRenderer.ColorInfo info) {
//        super(parent.textureManager, parent.font);
//        this.info = info;
//    }
//
//    @Override
//    public int drawString(String text, float x, float y, int color) {
//        float sine = 0.5F * ((float) Math.sin(Math.toRadians(4.0F * ((float) GlowingTextRenderer.getTicks() + Minecraft.getInstance().getRenderPartialTicks()))) + 1.0F);
//        return super.drawString(text, x, y, Utils.intColor(this.info.r + (int) (this.info.rl * sine), this.info.g + (int) (this.info.gl * sine), this.info.b + (int) (this.info.bl * sine)));
//    }
//
//    @Override
//    public int drawStringWithShadow(String text, float x, float y, int color) {
//        float sine = 0.5F * ((float) Math.sin(Math.toRadians(4.0F * ((float) GlowingTextRenderer.getTicks() + Minecraft.getInstance().getRenderPartialTicks()))) + 1.0F);
//        return super.drawStringWithShadow(text, x, y, Utils.intColor(this.info.r + (int) (this.info.rl * sine), this.info.g + (int) (this.info.gl * sine), this.info.b + (int) (this.info.bl * sine)));
//    }
//}

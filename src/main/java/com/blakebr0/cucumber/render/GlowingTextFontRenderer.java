package com.blakebr0.cucumber.render;

import net.minecraft.client.gui.FontRenderer;

public class GlowingTextFontRenderer extends FontRenderer {

    private final GlowingTextRenderer.ColorInfo info;

    public GlowingTextFontRenderer(FontRenderer parent, GlowingTextRenderer.ColorInfo info) {
        super(parent.textureManager, parent.font);
        this.info = info;
    }

    @Override
    public int drawStringWithShadow(String s, float x, float y, int color) {
        return GlowingTextRenderer.drawGlowingText(this, s, (int) x, (int) y, this.info);
    }
}

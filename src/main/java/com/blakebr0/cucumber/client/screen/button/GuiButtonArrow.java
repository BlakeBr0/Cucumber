package com.blakebr0.cucumber.client.screen.button;

import com.blakebr0.cucumber.Cucumber;
import com.blakebr0.cucumber.util.Localizable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

// TODO: 1.16: reevaluate
public class GuiButtonArrow extends IconButton {
	private static final ResourceLocation TEXTURE = new ResourceLocation(Cucumber.MOD_ID, "textures/gui/icons.png");
	
	public GuiButtonArrow(int id, int x, int y) {
		this(id, x, y, Localizable.of("tooltip.cu.back").build(), true);
	}
	
	public GuiButtonArrow(int id, int x, int y, ITextComponent text, boolean invert) {
		super(x, y, 29, 15, 24 + (invert ? 29 : 0), 0, text, TEXTURE);
	}
}

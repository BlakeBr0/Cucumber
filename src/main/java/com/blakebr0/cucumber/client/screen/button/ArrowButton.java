package com.blakebr0.cucumber.client.screen.button;

import com.blakebr0.cucumber.Cucumber;
import com.blakebr0.cucumber.util.Localizable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class ArrowButton extends IconButton {
	private static final ResourceLocation TEXTURE = new ResourceLocation(Cucumber.MOD_ID, "textures/gui/icons.png");
	
	public ArrowButton(int x, int y, IPressable onPress) {
		this(x, y, Localizable.of("tooltip.cucumber.back").build(), true, onPress);
	}
	
	public ArrowButton(int x, int y, ITextComponent text, boolean invert, IPressable onPress) {
		super(x, y, 29, 15, 24 + (invert ? 29 : 0), 0, text, TEXTURE, onPress);
	}
}

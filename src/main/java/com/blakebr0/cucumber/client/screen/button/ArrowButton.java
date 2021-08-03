package com.blakebr0.cucumber.client.screen.button;

import com.blakebr0.cucumber.Cucumber;
import com.blakebr0.cucumber.util.Localizable;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class ArrowButton extends IconButton {
	private static final ResourceLocation TEXTURE = new ResourceLocation(Cucumber.MOD_ID, "textures/gui/icons.png");
	
	public ArrowButton(int x, int y, OnPress onPress) {
		this(x, y, Localizable.of("tooltip.cucumber.back").build(), true, onPress);
	}
	
	public ArrowButton(int x, int y, Component text, boolean invert, OnPress onPress) {
		super(x, y, 29, 15, 24 + (invert ? 29 : 0), 0, text, TEXTURE, onPress);
	}
}

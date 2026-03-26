package com.blakebr0.cucumber.client.screen.button;

import com.blakebr0.cucumber.Cucumber;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

public class ArrowButton extends IconButton {
	private static final Identifier TEXTURE = Cucumber.resource("textures/gui/icons.png");
	
	public ArrowButton(int x, int y, OnPress onPress) {
		this(x, y, Component.translatable("tooltip.cucumber.back"), true, onPress);
	}
	
	public ArrowButton(int x, int y, Component text, boolean invert, OnPress onPress) {
		super(x, y, 29, 15, 24 + (invert ? 29 : 0), 0, text, TEXTURE, onPress);
	}
}

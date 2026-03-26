package com.blakebr0.cucumber.client.screen.button;

import com.blakebr0.cucumber.Cucumber;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

public class CloseButton extends IconButtonStatic {
	private static final Identifier TEXTURE = Cucumber.resource("textures/gui/icons.png");
	
	public CloseButton(int x, int y, OnPress onPress) {
		super(x, y, 9, 9, 14, 0, Component.translatable("tooltip.cucumber.close"), TEXTURE, onPress);
	}
}

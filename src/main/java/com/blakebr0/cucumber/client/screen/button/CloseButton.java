package com.blakebr0.cucumber.client.screen.button;

import com.blakebr0.cucumber.Cucumber;
import com.blakebr0.cucumber.util.Localizable;
import net.minecraft.util.ResourceLocation;

public class CloseButton extends IconButtonStatic {
	private static final ResourceLocation TEXTURE = new ResourceLocation(Cucumber.MOD_ID, "textures/gui/icons.png");
	
	public CloseButton(int x, int y, IPressable onPress) {
		super(x, y, 9, 9, 14, 0, Localizable.of("tooltip.cucumber.close").build(), TEXTURE, onPress);
	}
}

package com.blakebr0.cucumber.gui.button;

import com.blakebr0.cucumber.Cucumber;
import com.blakebr0.cucumber.util.Localizable;
import net.minecraft.util.ResourceLocation;

public class GuiButtonExit extends IconButtonStatic {
	private static final ResourceLocation TEXTURE = new ResourceLocation(Cucumber.MOD_ID, "textures/gui/icons.png");
	
	public GuiButtonExit(int x, int y) {
		super(x, y, 9, 9, 14, 0, Localizable.of("tooltip.cucumber.exit").build(), TEXTURE);
	}
}

package com.blakebr0.cucumber.gui.button;

import com.blakebr0.cucumber.Cucumber;
import com.blakebr0.cucumber.helper.ResourceHelper;
import com.blakebr0.cucumber.lib.Localizable;
import net.minecraft.util.ResourceLocation;

public class GuiButtonExit extends IconButtonStatic {
	private static final ResourceLocation TEXTURE = ResourceHelper.getResource(Cucumber.MOD_ID, "textures/gui/icons.png");
	
	public GuiButtonExit(int id, int x, int y) {
		super(x, y, 9, 9, 14, 0, Localizable.of("tooltip.cu.exit").buildString(), TEXTURE);
	}
}

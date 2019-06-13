package com.blakebr0.cucumber.gui.button;

import com.blakebr0.cucumber.Cucumber;
import com.blakebr0.cucumber.helper.ResourceHelper;
import com.blakebr0.cucumber.lib.Localizable;
import net.minecraft.util.ResourceLocation;

public class GuiButtonArrow extends IconButton {
	private static final ResourceLocation TEXTURE = ResourceHelper.getResource(Cucumber.MOD_ID, "textures/gui/icons.png");
	
	public GuiButtonArrow(int id, int x, int y) {
		this(id, x, y, Localizable.of("tooltip.cu.back").buildString(), true);
	}
	
	public GuiButtonArrow(int id, int x, int y, String text, boolean invert) {
		super(x, y, 29, 15, 24 + (invert ? 29 : 0), 0, text, TEXTURE);
	}
}

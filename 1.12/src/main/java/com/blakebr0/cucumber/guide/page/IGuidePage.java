package com.blakebr0.cucumber.guide.page;

import com.blakebr0.cucumber.guide.Guide;

public interface IGuidePage {

	void drawScreen(Guide guide, int mouseX, int mouseY, float partialTicks, int width, int height, int xSize, int ySize);
}

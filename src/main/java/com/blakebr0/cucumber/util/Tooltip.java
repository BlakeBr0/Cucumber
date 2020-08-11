package com.blakebr0.cucumber.util;

import net.minecraft.util.text.TextFormatting;

public class Tooltip extends Localizable {
    public Tooltip(String key) {
        super(key, TextFormatting.GRAY);
    }

    public Tooltip(String key, TextFormatting defaultColor) {
        super(key, defaultColor);
    }
}

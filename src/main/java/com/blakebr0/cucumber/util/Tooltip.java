package com.blakebr0.cucumber.util;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public class Tooltip {
    private final String key;

    private ChatFormatting color;
    private Object[] args;

    public Tooltip(String key) {
        this(key, ChatFormatting.GRAY);
    }

    public Tooltip(String key, ChatFormatting defaultColor) {
        this.key = key;
        this.color = defaultColor;
        this.args = new Object[0];
    }

    public Tooltip args(Object... args) {
        this.args = args;
        return this;
    }

    public Tooltip color(ChatFormatting color) {
        this.color = color;
        return this;
    }

    public MutableComponent toComponent() {
        return Component.translatable(this.key, this.args).withStyle(this.color);
    }

    public String toString() {
        return this.toComponent().getString();
    }
}

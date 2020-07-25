package com.blakebr0.cucumber.util;

import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

public class Localizable {
    private final String key;
    private final TextFormatting defaultColor;

    protected Localizable(String key) {
        this(key, null);
    }

    protected Localizable(String key, TextFormatting defaultColor) {
        this.key = key;
        this.defaultColor = defaultColor;
    }

    public static Localizable of(String key) {
        return new Localizable(key);
    }

    public static Localizable of(String key, TextFormatting defaultColor) {
        return new Localizable(key, defaultColor);
    }

    public String getKey() {
        return this.key;
    }

    public TextFormatting getDefaultColor() {
        return this.defaultColor;
    }

    public LocalizableBuilder args(Object... args) {
        return this.builder().args(args);
    }

    public LocalizableBuilder color(TextFormatting color) {
        return this.builder().color(color);
    }

    public IFormattableTextComponent build() {
        return this.builder().build();
    }

    public String buildString() {
        return this.builder().buildString();
    }

    private LocalizableBuilder builder() {
        return new LocalizableBuilder(this.key).color(this.defaultColor);
    }

    public static class LocalizableBuilder {
        private final String key;
        private Object[] args = new Object[0];
        private TextFormatting color;

        public LocalizableBuilder(String key) {
            this.key = key;
        }

        public LocalizableBuilder args(Object... args) {
            this.args = args;
            return this;
        }

        public LocalizableBuilder color(TextFormatting color) {
            this.color = color;
            return this;
        }

        public IFormattableTextComponent build() {
            IFormattableTextComponent component = new TranslationTextComponent(this.key, this.args);
            if (this.color != null)
                component.mergeStyle(this.color);

            return component;
        }

        public String buildString() {
            return this.build().getString();
        }
    }
}

package com.blakebr0.cucumber.util;

import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

public class Localizable {
    private final String key;

    private Localizable(String key) {
        this.key = key;
    }

    public static Localizable of(String key) {
        return new Localizable(key);
    }

    public String getKey() {
        return this.key;
    }

    public LocalizableBuilder args(Object... args) {
        return new LocalizableBuilder(this.key).args(args);
    }

    public LocalizableBuilder color(TextFormatting color) {
        return new LocalizableBuilder(this.key).color(color);
    }

    public IFormattableTextComponent build() {
        return new LocalizableBuilder(this.key).build();
    }

    public String buildString() {
        return new LocalizableBuilder(this.key).buildString();
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
                component.func_240701_a_(this.color);

            return component;
        }

        public String buildString() {
            return this.build().getString();
        }
    }
}

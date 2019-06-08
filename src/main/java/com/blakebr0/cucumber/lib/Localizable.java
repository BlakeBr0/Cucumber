package com.blakebr0.cucumber.lib;

import net.minecraft.util.text.ITextComponent;
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

    public ITextComponent build() {
        return new LocalizableBuilder(this.key).build();
    }

    public String buildString() {
        return new LocalizableBuilder(this.key).buildString();
    }

    public class LocalizableBuilder {
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

        public ITextComponent build() {
            ITextComponent component = new TranslationTextComponent(this.key, this.args);
            if (this.color != null)
                component.applyTextStyle(this.color);

            return component;
        }

        public String buildString() {
            return this.build().getFormattedText();
        }
    }
}

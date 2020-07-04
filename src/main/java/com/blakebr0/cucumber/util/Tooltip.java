package com.blakebr0.cucumber.util;

import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

public class Tooltip {
    private final String key;

    public Tooltip(String key) {
        this.key = key;
    }

    public String getKey() {
        return this.key;
    }

    public TooltipBuilder args(Object... args) {
        return new TooltipBuilder(this.key).args(args);
    }

    public TooltipBuilder color(TextFormatting color) {
        return new TooltipBuilder(this.key).color(color);
    }

    public ITextComponent build() {
        return new TooltipBuilder(this.key).build();
    }

    public String buildString() {
        return new TooltipBuilder(this.key).buildString();
    }

    public static class TooltipBuilder {
        private final String key;
        private Object[] args = new Object[0];
        private TextFormatting color;

        public TooltipBuilder(String key) {
            this.key = key;
        }

        public TooltipBuilder args(Object... args) {
            this.args = args;
            return this;
        }

        public TooltipBuilder color(TextFormatting color) {
            this.color = color;
            return this;
        }

        public IFormattableTextComponent build() {
            return new TranslationTextComponent(this.key, this.args).func_240699_a_(this.color != null ? this.color : TextFormatting.GRAY);
        }

        public String buildString() {
            return this.build().getString();
        }
    }
}

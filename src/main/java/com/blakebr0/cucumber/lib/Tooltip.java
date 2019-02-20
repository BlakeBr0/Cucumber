package com.blakebr0.cucumber.lib;

import com.blakebr0.cucumber.util.Utils;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;

public class Tooltip {

    private final String key;

    public Tooltip(String key) {
        this.key = key;
    }

    public String key() {
        return this.key;
    }

    public TooltipBuilder get() {
        return new TooltipBuilder(this.key);
    }

    public class TooltipBuilder {

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

        public ITextComponent build() {
            return this.color != null ? new TextComponentTranslation(this.key, this.args).applyTextStyle(this.color) : new TextComponentTranslation(this.key, this.args);
        }

        public String buildString() {
            return this.color != null ? this.color + Utils.localize(this.key, this.args) : Utils.localize(this.key, this.args);
        }
    }
}

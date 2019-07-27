package com.blakebr0.cucumber.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ModConfigs {
    public static final ForgeConfigSpec CLIENT;

    public static final ForgeConfigSpec.BooleanValue ENABLE_TAG_TOOLTIPS;

    static {
        final ForgeConfigSpec.Builder client = new ForgeConfigSpec.Builder();

        client.comment("General configuration options.").push("General");
        ENABLE_TAG_TOOLTIPS = client
                .comment("Enable tag list tooltips for blocks/items?")
                .translation("configGui.cucumber.enable_tag_tooltips")
                .define("tagTooltips", true);
        client.pop();

        CLIENT = client.build();
    }
}

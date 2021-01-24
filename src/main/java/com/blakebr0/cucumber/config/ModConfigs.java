package com.blakebr0.cucumber.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ModConfigs {
    public static final ForgeConfigSpec CLIENT;

    public static final ForgeConfigSpec.BooleanValue ENABLE_TAG_TOOLTIPS;
    public static final ForgeConfigSpec.BooleanValue ENABLE_NBT_TOOLTIPS;

    static {
        final ForgeConfigSpec.Builder client = new ForgeConfigSpec.Builder();

        client.comment("General configuration options.").push("General");
        ENABLE_TAG_TOOLTIPS = client
                .comment("Enable tag list tooltips for blocks/items?")
                .translation("configGui.cucumber.enable_tag_tooltips")
                .define("tagTooltips", true);
        ENABLE_NBT_TOOLTIPS = client
                .comment("Enable NBT tooltips for items?")
                .translation("configGui.cucumber.enable_nbt_tooltips")
                .define("nbtTooltips", false);
        client.pop();

        CLIENT = client.build();
    }
}

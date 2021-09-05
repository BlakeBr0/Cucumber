package com.blakebr0.cucumber.config;

import com.google.common.collect.Lists;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.List;

public class ModConfigs {
    public static final ForgeConfigSpec CLIENT;
    public static final ForgeConfigSpec COMMON;

    public static final ForgeConfigSpec.BooleanValue ENABLE_TAG_TOOLTIPS;
    public static final ForgeConfigSpec.BooleanValue ENABLE_NBT_TOOLTIPS;

    // Client
    static {
        final var client = new ForgeConfigSpec.Builder();

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

    public static final ForgeConfigSpec.ConfigValue<List<String>> MOD_TAG_PRIORITIES;

    // Common
    static {
        final var common = new ForgeConfigSpec.Builder();

        common.comment("General configuration options.").push("General");
        MOD_TAG_PRIORITIES = common
                .comment("Mod ids (in order) to prioritize using items for when generating the cucumber-tags.json file.")
                .translation("configGui.cucumber.mod_tag_priorities")
                .define("modTagPriorities", Lists.newArrayList("thermal", "mekanism", "tconstruct", "immersiveengineering", "appliedenergistics2"));
        common.pop();

        COMMON = common.build();
    }
}

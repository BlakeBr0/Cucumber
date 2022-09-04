package com.blakebr0.cucumber.config;

import com.blakebr0.cucumber.Cucumber;
import com.blakebr0.cucumber.util.FeatureFlag;
import net.minecraft.resources.ResourceLocation;

public final class ModFeatureFlags {
    public static final FeatureFlag NBT_TOOLTIPS = FeatureFlag.create(new ResourceLocation(Cucumber.MOD_ID, "nbt_tooltips"), ModConfigs.ENABLE_NBT_TOOLTIPS);
    public static final FeatureFlag TAG_TOOLTIPS = FeatureFlag.create(new ResourceLocation(Cucumber.MOD_ID, "tag_tooltips"), ModConfigs.ENABLE_TAG_TOOLTIPS);
}

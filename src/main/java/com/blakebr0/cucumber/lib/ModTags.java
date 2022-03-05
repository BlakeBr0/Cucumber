package com.blakebr0.cucumber.lib;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

import static com.blakebr0.cucumber.Cucumber.MOD_ID;

public final class ModTags {
    public static final TagKey<Block> MINEABLE_WITH_PAXEL = BlockTags.create(new ResourceLocation(MOD_ID, "mineable/paxel"));
}

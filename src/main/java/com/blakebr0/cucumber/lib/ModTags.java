package com.blakebr0.cucumber.lib;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.level.block.Block;

import static com.blakebr0.cucumber.Cucumber.MOD_ID;

public final class ModTags {
    public static final Tag.Named<Block> MINEABLE_WITH_PAXEL = BlockTags.createOptional(new ResourceLocation(MOD_ID, "mineable/paxel"));
}

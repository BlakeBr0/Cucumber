package com.blakebr0.cucumber.client.handler;

import com.blakebr0.cucumber.config.ModConfigs;
import com.blakebr0.cucumber.helper.FluidHelper;
import com.blakebr0.cucumber.lib.Tooltips;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

import java.util.Comparator;
import java.util.List;

public final class TagTooltipHandler {
    @SubscribeEvent(priority = EventPriority.LOW)
    public void onItemTooltip(ItemTooltipEvent event) {
        if (!ModConfigs.ENABLE_TAG_TOOLTIPS.get())
            return;

        if (Minecraft.getInstance().options.advancedItemTooltips) {
            var stack = event.getItemStack();
            var block = Block.byItem(stack.getItem());

            var blockTags = block == Blocks.AIR ? List.of() : block.defaultBlockState().tags()
                    .map(TagKey::location)
                    .toList();
            var itemTags = stack.tags()
                    .map(TagKey::location)
                    .toList();

            var fluidTags = FluidHelper.getFluidTags(stack).values().stream()
                    .flatMap(List::stream)
                    .distinct()
                    .sorted(Comparator.comparing(Identifier::toString))
                    .toList();

            if (!blockTags.isEmpty() || !itemTags.isEmpty() || !fluidTags.isEmpty()) {
                var tooltip = event.getToolTip();
                var flags = event.getFlags();

                if (flags.hasControlDown()) {
                    if (!blockTags.isEmpty()) {
                        tooltip.add(Tooltips.BLOCK_TAGS.toComponent());

                        for (var tag : blockTags) {
                            tooltip.add(Component.literal("  " + tag).withStyle(ChatFormatting.DARK_GRAY));
                        }
                    }

                    if (!itemTags.isEmpty()) {
                        tooltip.add(Tooltips.ITEM_TAGS.toComponent());

                        for (var tag : itemTags) {
                            tooltip.add(Component.literal("  " + tag).withStyle(ChatFormatting.DARK_GRAY));
                        }
                    }

                    if (!fluidTags.isEmpty()) {
                        tooltip.add(Tooltips.FLUID_TAGS.toComponent());

                        for (var tag : fluidTags) {
                            tooltip.add(Component.literal("  " + tag).withStyle(ChatFormatting.DARK_GRAY));
                        }
                    }
                } else {
                    tooltip.add(Tooltips.HOLD_CTRL_FOR_TAGS.toComponent());
                }
            }
        }
    }
}

package com.blakebr0.cucumber.handler;

import com.blakebr0.cucumber.config.ModConfigs;
import com.blakebr0.cucumber.lib.Tooltips;
import com.blakebr0.cucumber.util.Localizable;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.List;
import java.util.Set;

public final class TagTooltipHandler {
    @SubscribeEvent(priority = EventPriority.LOW)
    public void onItemTooltip(ItemTooltipEvent event) {
        if (!ModConfigs.ENABLE_TAG_TOOLTIPS.get())
            return;

        if (Minecraft.getInstance().gameSettings.advancedItemTooltips) {
            Item item = event.getItemStack().getItem();
            Set<ResourceLocation> blockTags = Block.getBlockFromItem(item).getTags();
            Set<ResourceLocation> itemTags = item.getTags();
            if (!blockTags.isEmpty() || !itemTags.isEmpty()) {
                List<ITextComponent> tooltip = event.getToolTip();
                if (Screen.hasControlDown()) {
                    if (!blockTags.isEmpty()) {
                        tooltip.add(Tooltips.BLOCK_TAGS.color(TextFormatting.DARK_GRAY).build());
                        blockTags.stream()
                                .map(Object::toString)
                                .map(s -> "  " + s)
                                .map(t -> Localizable.of(t).color(TextFormatting.DARK_GRAY).build())
                                .forEach(tooltip::add);
                    }

                    if (!itemTags.isEmpty()) {
                        tooltip.add(Tooltips.ITEM_TAGS.color(TextFormatting.DARK_GRAY).build());
                        itemTags.stream()
                                .map(Object::toString)
                                .map(s -> "  " + s)
                                .map(t -> Localizable.of(t).color(TextFormatting.DARK_GRAY).build())
                                .forEach(tooltip::add);
                    }
                } else {
                    tooltip.add(Tooltips.HOLD_CTRL_FOR_TAGS.color(TextFormatting.DARK_GRAY).build());
                }
            }
        }
    }
}

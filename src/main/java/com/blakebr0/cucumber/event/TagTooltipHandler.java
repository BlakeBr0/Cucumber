package com.blakebr0.cucumber.event;

import com.blakebr0.cucumber.lib.Tooltip;
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

public class TagTooltipHandler {
    @SubscribeEvent(priority = EventPriority.LOW)
    public void onRenderTooltip(ItemTooltipEvent event) {
        if (Minecraft.getInstance().gameSettings.advancedItemTooltips) {
            Item item = event.getItemStack().getItem();
            Set<ResourceLocation> blockTags = Block.getBlockFromItem(item).getTags();
            Set<ResourceLocation> itemTags = item.getTags();
            if (!blockTags.isEmpty() || !itemTags.isEmpty()) {
                List<ITextComponent> lines = event.getToolTip();
                if (Screen.hasControlDown()) {
                    if (!blockTags.isEmpty()) {
                        lines.add(new Tooltip("tooltip.cucumber.block_tags").color(TextFormatting.DARK_GRAY).build());
                        blockTags.stream()
                                .map(Object::toString)
                                .map(t -> new Tooltip(t).color(TextFormatting.DARK_GRAY).build())
                                .forEach(lines::add);
                    }

                    if (!itemTags.isEmpty()) {
                        lines.add(new Tooltip("tooltip.cucumber.item_tags").color(TextFormatting.DARK_GRAY).build());
                        itemTags.stream()
                                .map(Object::toString)
                                .map(t -> new Tooltip(t).color(TextFormatting.DARK_GRAY).build())
                                .forEach(lines::add);
                    }
                } else {
                    lines.add(new Tooltip("tooltip.cucumber.hold_ctrl_for_tags").color(TextFormatting.DARK_GRAY).build());
                }
            }
        }
    }
}

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
    public void onRenderTooltip(ItemTooltipEvent event) {
        if (!ModConfigs.ENABLE_TAG_TOOLTIPS.get())
            return;

        if (Minecraft.getInstance().gameSettings.advancedItemTooltips) {
            Item item = event.getItemStack().getItem();
            Set<ResourceLocation> blockTags = Block.getBlockFromItem(item).getTags();
            Set<ResourceLocation> itemTags = item.getTags();
            if (!blockTags.isEmpty() || !itemTags.isEmpty()) {
                List<ITextComponent> lines = event.getToolTip();
                if (Screen.func_231172_r_()) {
                    if (!blockTags.isEmpty()) {
                        lines.add(Tooltips.BLOCK_TAGS.color(TextFormatting.DARK_GRAY).build());
                        blockTags.stream()
                                .map(Object::toString)
                                .map(t -> Localizable.of(t).color(TextFormatting.DARK_GRAY).build())
                                .forEach(lines::add);
                    }

                    if (!itemTags.isEmpty()) {
                        lines.add(Tooltips.ITEM_TAGS.color(TextFormatting.DARK_GRAY).build());
                        itemTags.stream()
                                .map(Object::toString)
                                .map(t -> Localizable.of(t).color(TextFormatting.DARK_GRAY).build())
                                .forEach(lines::add);
                    }
                } else {
                    lines.add(Tooltips.HOLD_CTRL_FOR_TAGS.color(TextFormatting.DARK_GRAY).build());
                }
            }
        }
    }
}

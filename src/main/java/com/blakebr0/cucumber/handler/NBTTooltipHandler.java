package com.blakebr0.cucumber.handler;

import com.blakebr0.cucumber.config.ModConfigs;
import com.blakebr0.cucumber.lib.Colors;
import com.blakebr0.cucumber.lib.Tooltips;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.List;

public final class NBTTooltipHandler {
    @SubscribeEvent(priority = EventPriority.LOW)
    public void onItemTooltip(ItemTooltipEvent event) {
        if (!ModConfigs.ENABLE_NBT_TOOLTIPS.get())
            return;

        if (Minecraft.getInstance().options.advancedItemTooltips) {
            ItemStack stack = event.getItemStack();
            CompoundNBT tag = stack.getTag();

            if (tag != null) {
                List<ITextComponent> tooltip = event.getToolTip();

                if (Screen.hasAltDown()) {
                    String text = tag.getPrettyDisplay(" ", 1).getString();

                    tooltip.add(new StringTextComponent(Colors.DARK_GRAY + text));
                } else {
                    tooltip.add(Tooltips.HOLD_ALT_FOR_NBT.color(TextFormatting.DARK_GRAY).build());
                }
            }
        }
    }
}

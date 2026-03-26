package com.blakebr0.cucumber.client.handler;

import com.blakebr0.cucumber.config.ModConfigs;
import com.blakebr0.cucumber.lib.Tooltips;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.component.TypedDataComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.util.StringUtil;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

public final class DataComponentTooltipHandler {
    @SubscribeEvent(priority = EventPriority.LOW)
    public void onItemTooltip(ItemTooltipEvent event) {
        if (!ModConfigs.ENABLE_DATA_COMPONENT_TOOLTIPS.get())
            return;

        if (Minecraft.getInstance().options.advancedItemTooltips) {
            var stack = event.getItemStack();
            var components = stack.getComponents();
            var tooltip = event.getToolTip();
            var flags = event.getFlags();

            if (flags.hasAltDown()) {
                tooltip.add(Tooltips.DATA_COMPONENTS.toComponent());

                for (TypedDataComponent<?> component : components) {
                    tooltip.add(createTextComponent(component));
                }
            } else {
                tooltip.add(Tooltips.HOLD_ALT_FOR_DATA_COMPONENTS.toComponent());
            }
        }
    }

    private static Component createTextComponent(TypedDataComponent<?> component) {
        var text = " " + StringUtil.truncateStringIfNecessary(component.toString(), 50, true);
        return Component.literal(text).withStyle(ChatFormatting.DARK_GRAY);
    }
}

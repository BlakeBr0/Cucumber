package com.blakebr0.cucumber.util;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import java.text.NumberFormat;

public final class Formatting {
    public static Component number(Object value) {
        return Component.literal(NumberFormat.getInstance().format(value));
    }

    public static Component energy(Object value) {
        return Component.literal(number(value) + " FE");
    }

    public static Component perTick(Object value) {
        return Component.literal(number(value) + " /t");
    }

    public static Component energyPerTick(Object value) {
        return Component.literal(number(value) + " FE/t");
    }

    public static Component itemWithCount(ItemStack stack) {
        return itemWithCount(stack, stack.getCount());
    }

    public static Component itemWithCount(ItemStack stack, int count) {
        return Component.literal(count + "x " + stack.getHoverName());
    }
}

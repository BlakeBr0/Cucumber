package com.blakebr0.cucumber.util;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;

import java.text.NumberFormat;

public final class Formatting {
    public static MutableComponent number(Object value) {
        return Component.literal(NumberFormat.getInstance().format(value));
    }

    public static MutableComponent percent(Object value) {
        return number(value).append("%");
    }

    public static MutableComponent energy(Object value) {
        return number(value).append(" FE");
    }

    public static MutableComponent perTick(Object value) {
        return number(value).append(" /t");
    }

    public static MutableComponent energyPerTick(Object value) {
        return number(value).append(" FE/t");
    }

    public static MutableComponent itemWithCount(ItemStack stack) {
        return itemWithCount(stack, stack.getCount());
    }

    public static MutableComponent itemWithCount(ItemStack stack, int count) {
        return Component.literal(count + "x " + stack.getHoverName());
    }
}

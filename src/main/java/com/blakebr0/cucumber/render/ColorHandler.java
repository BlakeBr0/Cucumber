package com.blakebr0.cucumber.render;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.util.IItemProvider;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ColorHandler {
    private static final Map<IBlockColor, Block[]> BLOCKS_COLORS = Collections.synchronizedMap(new HashMap<>());
    private static final Map<IItemColor, IItemProvider[]> ITEMS_COLORS = Collections.synchronizedMap(new HashMap<>());

    public static void registerBlocks(IBlockColor color, Block... blocks) {
        BLOCKS_COLORS.put(color, blocks);
    }

    public static void registerItems(IItemColor color, IItemProvider... items) {
        ITEMS_COLORS.put(color, items);
    }

    @SubscribeEvent
    public void onRegisterBlockColors(ColorHandlerEvent.Block event) {
        BlockColors colors = event.getBlockColors();
        BLOCKS_COLORS.forEach(colors::register);
    }

    @SubscribeEvent
    public void onRegisterItemColors(ColorHandlerEvent.Item event) {
        ItemColors colors = event.getItemColors();
        ITEMS_COLORS.forEach(colors::register);
    }
}

package com.blakebr0.cucumber.render;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.util.IItemProvider;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ColorHandler {
    private final Map<IBlockColor, Block[]> blocks = Collections.synchronizedMap(new HashMap<>());
    private final Map<IItemColor, IItemProvider[]> items = Collections.synchronizedMap(new HashMap<>());

    public void registerBlocks(IBlockColor color, Block... blocks) {
        this.blocks.put(color, blocks);
    }

    public void registerItems(IItemColor color, IItemProvider... items) {
        this.items.put(color, items);
    }

    public ColorHandler() {
        FMLJavaModLoadingContext.get().getModEventBus().register(this);
    }

    @SubscribeEvent
    public void onRegisterBlockColors(ColorHandlerEvent.Block event) {
        BlockColors colors = event.getBlockColors();
        this.blocks.forEach(colors::register);
    }

    @SubscribeEvent
    public void onRegisterItemColors(ColorHandlerEvent.Item event) {
        ItemColors colors = event.getItemColors();
        this.items.forEach(colors::register);
    }
}

package com.blakebr0.cucumber.client.handler;

import com.blakebr0.cucumber.Cucumber;
import com.blakebr0.cucumber.iface.IColored;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

public final class TintSourceHandler {
    @SubscribeEvent
    public void onRegisterBlockTintSource(RegisterColorHandlersEvent.ItemTintSources event) {
        event.register(Cucumber.resource("item_colors"), IColored.ItemColors.MAP_CODEC);
        event.register(Cucumber.resource("item_block_colors"), IColored.ItemBlockColors.MAP_CODEC);
    }
}

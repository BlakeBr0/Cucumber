package com.blakebr0.cucumber.client.handler;

import com.blakebr0.cucumber.Cucumber;
import com.blakebr0.cucumber.client.properties.CustomBowPullProperty;
import com.blakebr0.cucumber.client.properties.CustomCrossbowPullProperty;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RegisterRangeSelectItemModelPropertyEvent;

public final class ItemModelPropertyHandler {
    @SubscribeEvent
    public void onRegisterRangeSelectItemModelProperties(RegisterRangeSelectItemModelPropertyEvent event) {
        event.register(Cucumber.resource("custom_bow_pull"), CustomBowPullProperty.MAP_CODEC);
        event.register(Cucumber.resource("custom_crossbow_pull"), CustomCrossbowPullProperty.MAP_CODEC);
    }
}

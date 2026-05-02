package com.blakebr0.cucumber.event;

import net.minecraft.client.renderer.item.ClientItem;
import net.minecraft.resources.Identifier;
import net.neoforged.bus.api.Event;

import java.util.Map;

public class RegisterClientItemsEvent extends Event {
    private final Map<Identifier, ClientItem> currentClientItems;
    private final Map<Identifier, ClientItem> newClientItems;

    public RegisterClientItemsEvent(Map<Identifier, ClientItem> currentClientItems, Map<Identifier, ClientItem> newClientItems) {
        this.currentClientItems = currentClientItems;
        this.newClientItems = newClientItems;
    }

    public void register(Identifier id, ClientItem item) {
        this.newClientItems.put(id, item);
    }

    public boolean hasClientItem(Identifier id) {
        return this.currentClientItems.containsKey(id);
    }
}

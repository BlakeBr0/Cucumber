package com.blakebr0.cucumber.event;

import net.minecraft.world.item.crafting.RecipeManager;
import net.neoforged.bus.api.Event;

public class RecipeManagerLoadedEvent extends Event {
    private final RecipeManager manager;

    public RecipeManagerLoadedEvent(RecipeManager manager) {
        this.manager = manager;
    }

    public RecipeManager getRecipeManager() {
        return this.manager;
    }
}

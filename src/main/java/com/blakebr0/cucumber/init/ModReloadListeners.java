package com.blakebr0.cucumber.init;

import com.blakebr0.cucumber.event.RegisterRecipesEvent;
import com.blakebr0.cucumber.helper.RecipeHelper;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ModReloadListeners {
    @SubscribeEvent
    public void onAddReloadListeners(AddReloadListenerEvent event) {
        event.addListener(new RegisterRecipesReloadListener());
    }

    private static class RegisterRecipesReloadListener implements ResourceManagerReloadListener {
        @Override
        public void onResourceManagerReload(ResourceManager manager) {
            MinecraftForge.EVENT_BUS.post(new RegisterRecipesEvent(RecipeHelper.getRecipeManager()));
        }
    }
}

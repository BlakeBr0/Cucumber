package com.blakebr0.cucumber.crafting;

import com.blakebr0.cucumber.crafting.conditions.EnableableCondition;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ModRecipeSerializers {
    @SubscribeEvent
    public void onRegisterSerializers(RegistryEvent.Register<IRecipeSerializer<?>> event) {
        CraftingHelper.register(EnableableCondition.Serializer.INSTANCE);
    }
}

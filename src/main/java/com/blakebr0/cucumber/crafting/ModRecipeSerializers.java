package com.blakebr0.cucumber.crafting;

import com.blakebr0.cucumber.Cucumber;
import com.blakebr0.cucumber.crafting.conditions.EnableableCondition;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

public class ModRecipeSerializers {
    public static final IRecipeSerializer<?> CRAFTING_SHAPED_NO_MIRROR = new ShapedNoMirrorRecipe.Serializer();

    @SubscribeEvent
    public void onRegisterSerializers(RegistryEvent.Register<IRecipeSerializer<?>> event) {
        IForgeRegistry<IRecipeSerializer<?>> registry = event.getRegistry();

        registry.register(CRAFTING_SHAPED_NO_MIRROR.setRegistryName(new ResourceLocation(Cucumber.MOD_ID, "shaped_no_mirror")));

        CraftingHelper.register(EnableableCondition.Serializer.INSTANCE);
    }
}

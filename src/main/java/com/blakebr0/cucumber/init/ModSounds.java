package com.blakebr0.cucumber.init;

import com.blakebr0.cucumber.Cucumber;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModSounds {
    public static final DeferredRegister<SoundEvent> REGISTRY = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Cucumber.MOD_ID);

    public static final RegistryObject<SoundEvent> WATERING_CAN = REGISTRY.register("watering_can", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(Cucumber.MOD_ID, "watering_can")));
}

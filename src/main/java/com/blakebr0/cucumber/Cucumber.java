package com.blakebr0.cucumber;

import com.blakebr0.cucumber.network.NetworkHandler;
import com.blakebr0.cucumber.render.GlowingTextRenderer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Cucumber.MOD_ID)
public class Cucumber {

	public static final String NAME = "Cucumber Library";
	public static final String MOD_ID = "cucumber";
	public static final String VERSION = "${version}";

	public Cucumber() {
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::preInit);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::init);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::postInit);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientInit);
	}

	public void preInit(FMLCommonSetupEvent event) {
		NetworkHandler.init();
	}

	public void init(InterModEnqueueEvent event) {

	}

	public void postInit(InterModProcessEvent event) {

 	}

	public void clientInit(FMLClientSetupEvent event) {
		MinecraftForge.EVENT_BUS.register(new GlowingTextRenderer());
	}
}

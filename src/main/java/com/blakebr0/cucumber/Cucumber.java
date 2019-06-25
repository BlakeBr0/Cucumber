package com.blakebr0.cucumber;

import com.blakebr0.cucumber.event.BowFovHandler;
import com.blakebr0.cucumber.event.TagTooltipHandler;
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
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onCommonSetup);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onInterModEnqueue);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onInterModProcess);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onClientSetup);
	}

	public void onCommonSetup(FMLCommonSetupEvent event) {
		NetworkHandler.onCommonSetup();
	}

	public void onInterModEnqueue(InterModEnqueueEvent event) {

	}

	public void onInterModProcess(InterModProcessEvent event) {

 	}

	public void onClientSetup(FMLClientSetupEvent event) {
		MinecraftForge.EVENT_BUS.register(new GlowingTextRenderer());
		MinecraftForge.EVENT_BUS.register(new BowFovHandler());
		MinecraftForge.EVENT_BUS.register(new TagTooltipHandler());
	}
}

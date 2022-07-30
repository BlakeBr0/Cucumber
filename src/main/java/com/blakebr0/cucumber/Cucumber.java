package com.blakebr0.cucumber;

import com.blakebr0.cucumber.command.ModCommands;
import com.blakebr0.cucumber.config.ModConfigs;
import com.blakebr0.cucumber.crafting.TagMapper;
import com.blakebr0.cucumber.handler.BowFOVHandler;
import com.blakebr0.cucumber.handler.NBTTooltipHandler;
import com.blakebr0.cucumber.handler.TagTooltipHandler;
import com.blakebr0.cucumber.helper.RecipeHelper;
import com.blakebr0.cucumber.init.ModRecipeSerializers;
import com.blakebr0.cucumber.init.ModReloadListeners;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(Cucumber.MOD_ID)
public final class Cucumber {
	public static final String NAME = "Cucumber Library";
	public static final String MOD_ID = "cucumber";
	public static final Logger LOGGER = LoggerFactory.getLogger(NAME);

	public Cucumber() {
		var bus = FMLJavaModLoadingContext.get().getModEventBus();

		bus.register(this);
		bus.register(new ModRecipeSerializers());

		ModRecipeSerializers.REGISTRY.register(bus);

		ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ModConfigs.CLIENT);
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ModConfigs.COMMON);
	}

	@SubscribeEvent
	public void onCommonSetup(FMLCommonSetupEvent event) {
		MinecraftForge.EVENT_BUS.register(new ModCommands());
		MinecraftForge.EVENT_BUS.register(new ModReloadListeners());
		MinecraftForge.EVENT_BUS.register(new RecipeHelper());
		MinecraftForge.EVENT_BUS.register(new TagMapper());
	}

 	@SubscribeEvent
	public void onClientSetup(FMLClientSetupEvent event) {
		MinecraftForge.EVENT_BUS.register(new BowFOVHandler());
		MinecraftForge.EVENT_BUS.register(new TagTooltipHandler());
		MinecraftForge.EVENT_BUS.register(new NBTTooltipHandler());
	}
}

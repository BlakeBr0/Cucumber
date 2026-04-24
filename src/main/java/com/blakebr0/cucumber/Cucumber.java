package com.blakebr0.cucumber;

import com.blakebr0.cucumber.client.ModRenderTypes;
import com.blakebr0.cucumber.client.handler.BowFOVHandler;
import com.blakebr0.cucumber.client.handler.DataComponentTooltipHandler;
import com.blakebr0.cucumber.client.handler.ItemModelPropertyHandler;
import com.blakebr0.cucumber.client.handler.TagTooltipHandler;
import com.blakebr0.cucumber.client.handler.TintSourceHandler;
import com.blakebr0.cucumber.command.ModCommands;
import com.blakebr0.cucumber.config.ModConfigs;
import com.blakebr0.cucumber.crafting.TagMapper;
import com.blakebr0.cucumber.init.ModConditionSerializers;
import com.blakebr0.cucumber.init.ModDataComponentTypes;
import com.blakebr0.cucumber.init.ModRecipeSerializers;
import com.blakebr0.cucumber.init.ModSounds;
import com.blakebr0.cucumber.util.FeatureFlagInitializer;
import net.minecraft.resources.Identifier;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(Cucumber.MOD_ID)
public final class Cucumber {
	public static final String NAME = "Cucumber Library";
	public static final String MOD_ID = "cucumber";
	public static final Logger LOGGER = LoggerFactory.getLogger(NAME);

	public Cucumber(IEventBus bus, ModContainer mod) {
		bus.register(this);

		ModDataComponentTypes.REGISTRY.register(bus);
		ModSounds.REGISTRY.register(bus);
		ModConditionSerializers.REGISTRY.register(bus);
		ModRecipeSerializers.REGISTRY.register(bus);

		if (FMLEnvironment.getDist() == Dist.CLIENT) {
			bus.register(new ItemModelPropertyHandler());
			bus.register(new TintSourceHandler());
			bus.register(new ModRenderTypes());
		}

		FeatureFlagInitializer.init();

		mod.registerConfig(ModConfig.Type.CLIENT, ModConfigs.CLIENT);
		mod.registerConfig(ModConfig.Type.COMMON, ModConfigs.COMMON);
	}

	@SubscribeEvent
	public void onCommonSetup(FMLCommonSetupEvent event) {
		NeoForge.EVENT_BUS.register(new ModCommands());
		NeoForge.EVENT_BUS.register(new TagMapper());
	}

 	@SubscribeEvent
	public void onClientSetup(FMLClientSetupEvent event) {
		NeoForge.EVENT_BUS.register(new BowFOVHandler());
		NeoForge.EVENT_BUS.register(new TagTooltipHandler());
		NeoForge.EVENT_BUS.register(new DataComponentTooltipHandler());
	}

	public static Identifier resource(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}

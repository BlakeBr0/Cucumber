package com.blakebr0.cucumber;

import com.blakebr0.cucumber.proxy.CommonProxy;
import com.blakebr0.cucumber.registry.ModRegistry;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(name = Cucumber.NAME, modid = Cucumber.MOD_ID, version = Cucumber.VERSION)
public class Cucumber {

	public static final String NAME = "Cucumber Library";
	public static final String MOD_ID = "cucumber";
	public static final String VERSION = "${version}";
	
	public static final ModRegistry REGISTRY = ModRegistry.create(MOD_ID);
	
	@SidedProxy(clientSide = "com.blakebr0.cucumber.proxy.ClientProxy", serverSide = "com.blakebr0.cucumber.proxy.ServerProxy")
	public static CommonProxy proxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		proxy.preInit(event);
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.init(event);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		proxy.postInit(event);
	}
}

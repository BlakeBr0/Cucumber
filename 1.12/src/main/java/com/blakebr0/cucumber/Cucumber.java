package com.blakebr0.cucumber;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(name = Cucumber.NAME, modid = Cucumber.MOD_ID, version = Cucumber.VERSION)
public class Cucumber {

	public static final String NAME = "Cucumber";
	public static final String MOD_ID = "cucumber";
	public static final String VERSION = "${version}";

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {

	}

	@EventHandler
	public void init(FMLInitializationEvent event){
		
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {

	}
}

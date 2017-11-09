package com.blakebr0.cucumber.proxy;

import com.blakebr0.cucumber.render.GlowingTextRenderer;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {
	
	public void preInit(FMLPreInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(new GlowingTextRenderer());
	}
	
	public void init(FMLInitializationEvent event) {
		//GlowingTextRenderer.addStack(new ItemStack(Items.APPLE), GlowingTextRenderer.EPIC);
	}
	
	public void postInit(FMLPostInitializationEvent event) {
		
	}
}

package com.blakebr0.cucumber.proxy;

import com.blakebr0.cucumber.Cucumber;
import com.blakebr0.cucumber.guide.Guide;
import com.blakebr0.cucumber.guide.GuideEntry;
import com.blakebr0.cucumber.render.GlowingTextRenderer;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {
	
	public void preInit(FMLPreInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(new GlowingTextRenderer());
		Guide.create(Cucumber.MOD_ID, "guide.cucumber.name", "guide_test", 0x123456, CreativeTabs.MATERIALS).addEntry(new GuideEntry(0)).register();
	}
	
	public void init(FMLInitializationEvent event) {
		//GlowingTextRenderer.addStack(new ItemStack(Items.APPLE), GlowingTextRenderer.EPIC);
	}
	
	public void postInit(FMLPostInitializationEvent event) {
		
	}
}

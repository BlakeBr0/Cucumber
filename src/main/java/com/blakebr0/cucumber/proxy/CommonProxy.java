package com.blakebr0.cucumber.proxy;

import com.blakebr0.cucumber.Cucumber;
import com.blakebr0.cucumber.event.MobDropHandler;
import com.blakebr0.cucumber.guide.Guide;
import com.blakebr0.cucumber.guide.GuideEntry;
import com.blakebr0.cucumber.guide.components.ComponentText;
import com.blakebr0.cucumber.helper.StackHelper;
import com.blakebr0.cucumber.network.NetworkHandler;
import com.blakebr0.cucumber.render.GlowingTextRenderer;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.passive.EntityPig;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {
	
	public void preInit(FMLPreInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(new GlowingTextRenderer());
		Guide g = Guide.create(Cucumber.NAME, "guide.cucumber.name", "BlakeBr0", "guide_test", 0x123456, CreativeTabs.MATERIALS);
		g.addEntry(new GuideEntry(0, "the ting goes SKRRRA XD")
				.addComponent(new ComponentText("Hello theorists! Welcome to GAME THEORY!"))
				.addComponent(new ComponentText("WOAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH"
						+ "WOAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH"
						+ "WOAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH"
						+ "WOAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH"
						+ "WOAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH"
						+ "WOAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH")));
		g.addEntry(new GuideEntry(1, "the ting goes SKRRRA XD"));
		g.addEntry(new GuideEntry(2, "the ting goes SKRRRA XD"));
		g.addEntry(new GuideEntry(3, "the ting goes SKRRRA XD"));
		g.addEntry(new GuideEntry(4, "the ting goes SKRRRA XD"));
		g.addEntry(new GuideEntry(5, "the ting goes SKRRRA XD"));
		g.addEntry(new GuideEntry(6, "the ting goes SKRRRA XD"));
		g.addEntry(new GuideEntry(7, "the ting goes SKRRRA XD"));
		g.addEntry(new GuideEntry(8, "the ting goes SKRRRA XD"));
		g.addEntry(new GuideEntry(9, "the ting goes SKRRRA XD"));
		g.addEntry(new GuideEntry(10, "the ting goes SKRRRA XD"));
		g.addEntry(new GuideEntry(11, "the ting goes SKRRRA XD"));
		g.addEntry(new GuideEntry(12, "the ting goes SKRRRA XD"));
		g.addEntry(new GuideEntry(13, "the ting goes SKRRRA XD"));
		g.register(); 
	}
	
	public void init(FMLInitializationEvent event) {
		NetworkHandler.initialize();
		//MobDropHandler.register((drops, attacked, source, looting) -> {
		//	if (attacked instanceof EntityPig) {
		//		drops.add(StackHelper.toEntity(stack, attacked.getEntityWorld(), x, y, z))
		//	}
		//});
	}
	
	public void postInit(FMLPostInitializationEvent event) {
		
	}
}

package com.blakebr0.cucumber.proxy;

import com.blakebr0.cucumber.Cucumber;
import com.blakebr0.cucumber.crafting.OreOutputRecipeValidator;
import com.blakebr0.cucumber.event.MobDropHandler;
import com.blakebr0.cucumber.guide.Guide;
import com.blakebr0.cucumber.guide.GuideEntry;
import com.blakebr0.cucumber.guide.ItemGuide;
import com.blakebr0.cucumber.guide.pages.PageText;
import com.blakebr0.cucumber.helper.StackHelper;
import com.blakebr0.cucumber.network.NetworkHandler;
import com.blakebr0.cucumber.render.GlowingTextRenderer;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {
	
	public void preInit(FMLPreInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(new GlowingTextRenderer());
		/*
		Guide g = Guide.create("guide.cucumber.name", "BlakeBr0", 0x123456);
		g.addEntry("the ting goes SKRRRA XDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD")
				.setIconStack(new ItemStack(Blocks.STAINED_GLASS_PANE, 1, 2))
				.addPage(new PageText("Hello theorists! Welcome to GAME THEORY!"))
				.addPage(new PageText("WOAAAAAAAAAAAAAA<b>AAA<i>AAA<r>AAAAAAAA<red>AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH"
						+ "WOAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH"
						+ "WOAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH"
						+ "WOAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH"
						+ "WOAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH"
						+ "WOAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH"));
		g.addEntry("the ting goes SKRRRA XDDDDDDDDDD")
				.setIconStack(new ItemStack(Items.BEEF));
		g.addEntry("the ting goes SKRRRA XD")
				.setIconStack(new ItemStack(Blocks.COBBLESTONE));
		g.addEntry("the ting goes SKRRA XD");
		g.addEntry("the ting goes SKRRRA XD");
		g.addEntry("the ting goes SKRRA XD");
		g.addEntry( "the ting goes SKRRRA XD");
		g.addEntry("the ting goes SKRRRA XD");
		g.addEntry("the tinggoes SKRRRA XD");
		g.addEntry("the ting goes SKRRA XD");
		g.addEntry("the tin goes SKRRRA XD");
		g.addEntry("the tig goes SKRRRA XD");
		g.addEntry("the ting goes SKRRRA XD");
		g.addEntry("the ting goes SKRRA XD");
		g.addEntry("the tinggoes SKRRRA XD");
		g.addEntry("he ting goes SKRRRA XD");
		g.addEntry("the ting goes SKRRRA XD");
		g.addEntry("the ting goes SKRRA XD");
		g.addEntry("theting goes KRRRA XD");
		g.addEntry("the ting goes SKRRRA XD");
		g.addEntry("the tin goes SKRRRA XD");
		g.addEntry("the ting goes SKRRRA XD");
		g.addEntry("the ting goes SKRRRA XD");
		g.addEntry("thetig goes SKRRRA XD");
		g.addEntry("the ting goe SKRRRA XD");
		g.addEntry("the ting goes SKRRRA XD");
		g.addEntry("the ting ges SKRRRA XD");
		g.addEntry("the ting gos SKRRRA XD");
		g.addEntry("the ting goes SKRRRA XD");
		g.addEntry("the tingos SKRRRA XD");
		g.addEntry("the ting goes SKRRRA XD");
		g.addEntry("he ting goes SKRRRA XD");
		g.addEntry("the ting goes SKRRRA XD");
		g.addEntry("the ing goes SKRRRA XD");
		g.addEntry("the ting goes SKRRRA XD");
		g.addEntry("th ting goes SKRRRA XD");
		g.addEntry("the ting goes RA XD");
		
		Cucumber.REGISTRY.register(new ItemGuide("cucumber_guide", CreativeTabs.MATERIALS, g), "guide");*/
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
		OreOutputRecipeValidator.validate();
	}
}

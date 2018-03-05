package com.blakebr0.cucumber.proxy;

import com.blakebr0.cucumber.guide.Guide;
import com.blakebr0.cucumber.guide.ItemGuide;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);
	}
	
	@Override
	public void init(FMLInitializationEvent event) {
		super.init(event);
		
		if (Guide.hasGuideItems()) {
			ItemColors colors = Minecraft.getMinecraft().getItemColors();
			
			colors.registerItemColorHandler((stack, tint) -> {
				ItemGuide item = (ItemGuide) stack.getItem();
				return tint == 0 ? item.getGuide().getColor() : -1;
			},  Guide.getGuideItems().toArray(new ItemGuide[0]));
		}
	}
	
	@Override
	public void postInit(FMLPostInitializationEvent event) {
		super.postInit(event);
	}
}

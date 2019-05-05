package com.blakebr0.cucumber.helper;

import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.util.ResourceLocation;

public class ResourceHelper {
	public static ResourceLocation getResource(String modid, String resource) {
		return new ResourceLocation(modid, resource);
	}
	
	public static ModelResourceLocation getModelResource(String modid, String resource, String variant) {
		return new ModelResourceLocation(getResource(modid, resource), variant);
	}
}

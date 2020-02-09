package com.blakebr0.cucumber.network;

import com.blakebr0.cucumber.Cucumber;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class NetworkHandler {
	public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(Cucumber.MOD_ID, Cucumber.MOD_ID), () -> "1.0", s -> true, s -> true);
	
	public static void onCommonSetup() {

	}

	private static int id = 0;
	private static int id() {
		return id++;
	}
}

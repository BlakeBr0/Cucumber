package com.blakebr0.cucumber.network;

import com.blakebr0.cucumber.Cucumber;
import com.blakebr0.cucumber.helper.ResourceHelper;
import com.blakebr0.cucumber.network.messages.MessageUpdateGuideNBT;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class NetworkHandler {

	public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(ResourceHelper.getResource(Cucumber.MOD_ID, Cucumber.MOD_ID), () -> "1.0", s -> true, s -> true);
	
	public static void init() {
		INSTANCE.registerMessage(id(), MessageUpdateGuideNBT.class, MessageUpdateGuideNBT::write, MessageUpdateGuideNBT::read, MessageUpdateGuideNBT::handle);
	}

	private static int id = 0;
	private static int id() {
		return id++;
	}
}

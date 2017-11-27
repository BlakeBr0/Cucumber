package com.blakebr0.cucumber.network;

import com.blakebr0.cucumber.Cucumber;
import com.blakebr0.cucumber.network.messages.MessageUpdateGuideNBT;

import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class NetworkHandler {

	public static final SimpleNetworkWrapper NETWORK = new SimpleNetworkWrapper(Cucumber.MOD_ID);
	
	public static void initialize() {
		NETWORK.registerMessage(MessageUpdateGuideNBT.Handler.class, MessageUpdateGuideNBT.class, 0, Side.SERVER);
	}
}

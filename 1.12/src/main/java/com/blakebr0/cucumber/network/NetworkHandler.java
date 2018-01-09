package com.blakebr0.cucumber.network;

import com.blakebr0.cucumber.Cucumber;
import com.blakebr0.cucumber.network.messages.MessageUpdateGuideNBT;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class NetworkHandler {

	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Cucumber.MOD_ID);
	
	public static void initialize() {
		INSTANCE.registerMessage(MessageUpdateGuideNBT.Handler.class, MessageUpdateGuideNBT.class, 0, Side.SERVER);
	}
}

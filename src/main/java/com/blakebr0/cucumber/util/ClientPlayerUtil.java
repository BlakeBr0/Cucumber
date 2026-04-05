package com.blakebr0.cucumber.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;

public final class ClientPlayerUtil {
    public static LocalPlayer getClientPlayer() {
        return Minecraft.getInstance().player;
    }
}

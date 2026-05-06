package com.blakebr0.cucumber.util;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;

public final class ClientPlayerUtil {
    public static Player getClientPlayer() {
        return Minecraft.getInstance().player;
    }
}

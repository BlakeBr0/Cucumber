package com.blakebr0.cucumber.util;

import com.blakebr0.cucumber.client.sound.WateringCanSound;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;

public final class WateringCanUtil {
    public static void startPlayingSound(Player player) {
        if (player.level().isClientSide() && !WateringCanSound.playing(player.getId())) {
            Minecraft.getInstance().getSoundManager().play(new WateringCanSound(player));
        }
    }

    /**
     * Stops playing the watering can sound for the given player on the client side
     * @param player the player
     */
    public static void stopPlayingSound(Player player) {
        if (player.level().isClientSide() && WateringCanSound.playing(player.getId())) {
            WateringCanSound.stop(player.getId());
        }
    }
}

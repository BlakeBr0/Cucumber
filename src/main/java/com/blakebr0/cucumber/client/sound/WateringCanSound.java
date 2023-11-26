package com.blakebr0.cucumber.client.sound;

import com.blakebr0.cucumber.init.ModSounds;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class WateringCanSound extends AbstractTickableSoundInstance {
    private static final Map<Integer, WateringCanSound> PLAYING_FOR = Collections.synchronizedMap(new HashMap<>());
    private final Player player;
    private final ItemStack useItem;

    public WateringCanSound(Player player) {
        super(ModSounds.WATERING_CAN.get(), SoundSource.PLAYERS, player.getRandom());
        this.player = player;
        this.useItem = player.getUseItem();
        this.looping = true;
        this.volume = 0.5F;

        PLAYING_FOR.put(player.getId(), this);
    }

    public static boolean playing(int entityId) {
        return PLAYING_FOR.containsKey(entityId) && PLAYING_FOR.get(entityId) != null;
    }

    public static void stop(int entityId) {
        synchronized (PLAYING_FOR) {
            var sound = PLAYING_FOR.remove(entityId);
            if (sound != null)
                sound.stop();
        }
    }

    @Override
    public void tick() {
        // in the normal case of holding right-click to use a Watering Can, we can check when the
        // useItem changes to know if the sound should stop
        if (this.player.getUseItem() != this.useItem) {
            synchronized (PLAYING_FOR) {
                PLAYING_FOR.remove(this.player.getId());
                this.stop();
            }
        }
    }
}

package com.blakebr0.cucumber.item.tool;

import com.blakebr0.cucumber.iface.ICustomBow;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.function.Function;

public class BaseCrossbowItem extends CrossbowItem implements ICustomBow {
    public BaseCrossbowItem(Identifier id, Function<Properties, Properties> properties) {
        super(properties.apply(new Properties().setId(ResourceKey.create(Registries.ITEM, id))));
    }

    @Override // copied from CrossbowItem#releaseUsing
    public boolean releaseUsing(ItemStack stack, Level level, LivingEntity entity, int timeLeft) {
        // change: account for draw speed multiplier
        int i = (int) ((this.getUseDuration(stack, entity) - timeLeft) * this.getDrawSpeedMulti(stack));
        float f = getPowerForTime(i, stack, entity);
        if (f >= 1.0F && !isCharged(stack) && tryLoadProjectiles(entity, stack)) {
            var sounds = this.getChargingSounds(stack);

            sounds.end().ifPresent(
                    sound -> level.playSound(
                            null,
                            entity.getX(),
                            entity.getY(),
                            entity.getZ(),
                            sound.value(),
                            entity.getSoundSource(),
                            1.0F,
                            1.0F / (level.getRandom().nextFloat() * 0.5F + 1.0F) + 0.2F
                    )
            );

            return true;
        }

        return false;
    }

    @Override // copied from CrossbowItem#onUseTick
    public void onUseTick(Level level, LivingEntity entity, ItemStack stack, int timeLeft) {
        if (!level.isClientSide()) {
            var sounds = this.getChargingSounds(stack);
            float f = (float) ((int) ((this.getUseDuration(stack, entity) - timeLeft) * this.getDrawSpeedMulti(stack))) / (float) getChargeDuration(stack, entity);
            if (f < 0.2F) {
                this.startSoundPlayed = false;
                this.midLoadSoundPlayed = false;
            }

            if (f >= 0.2F && !this.startSoundPlayed) {
                this.startSoundPlayed = true;
                sounds.start()
                        .ifPresent(
                                p_352849_ -> level.playSound(
                                        null, entity.getX(), entity.getY(), entity.getZ(), p_352849_.value(), SoundSource.PLAYERS, 0.5F, 1.0F
                                )
                        );
            }

            if (f >= 0.5F && !this.midLoadSoundPlayed) {
                this.midLoadSoundPlayed = true;
                sounds.mid()
                        .ifPresent(
                                p_352855_ -> level.playSound(
                                        null, entity.getX(), entity.getY(), entity.getZ(), p_352855_.value(), SoundSource.PLAYERS, 0.5F, 1.0F
                                )
                        );
            }
        }
    }

    @Override
    public boolean hasFOVChange() {
        return false;
    }
}

package com.blakebr0.cucumber.network.message;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public abstract class Message<T extends Message<T>> {
    public abstract T read(FriendlyByteBuf buffer);
    public abstract void write(T message, FriendlyByteBuf buffer);
    public abstract void onMessage(T message, Supplier<NetworkEvent.Context> context);
}

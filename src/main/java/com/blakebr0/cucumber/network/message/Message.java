package com.blakebr0.cucumber.network.message;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public abstract class Message<T extends Message<T>> {
    public abstract T read(PacketBuffer buffer);
    public abstract void write(T message, PacketBuffer buffer);
    public abstract void onMessage(T message, Supplier<NetworkEvent.Context> context);
}

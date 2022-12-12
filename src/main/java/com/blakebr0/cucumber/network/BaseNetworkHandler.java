package com.blakebr0.cucumber.network;

import com.blakebr0.cucumber.network.message.LoginMessage;
import com.blakebr0.cucumber.network.message.Message;
import net.minecraft.network.Connection;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.HandshakeHandler;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class BaseNetworkHandler {
    private final SimpleChannel channel;
    private int id = 0;

    public BaseNetworkHandler(ResourceLocation id) {
        this.channel = NetworkRegistry.newSimpleChannel(id, () -> "1.0", (s) -> true, (s) -> true);
    }

    public SimpleChannel getChannel() {
        return this.channel;
    }

    public <T extends Message<T>> void register(Class<T> clazz, Message<T> message) {
        this.channel.messageBuilder(clazz, this.id++)
                .encoder(message::write)
                .decoder(message::read)
                .consumerMainThread(message::onMessage)
                .add();
    }

    public <T extends LoginMessage<T>> void register(Class<T> clazz, LoginMessage<T> message) {
        this.channel.messageBuilder(clazz, this.id++)
                .encoder(message::write)
                .decoder(message::read)
                .consumerMainThread((loginMessage, context) -> {
                    BiConsumer<T, Supplier<NetworkEvent.Context>> handler;

                    if (context.get().getDirection().getReceptionSide().isServer()) {
                        handler = HandshakeHandler.indexFirst((handshake, msg, ctx) -> message.onMessage(msg, ctx));
                    } else {
                        handler = message::onMessage;
                    }

                    handler.accept(loginMessage, context);
                })
                .loginIndex(LoginMessage::getLoginIndex, LoginMessage::setLoginIndex)
                .markAsLoginPacket()
                .add();
    }

    public <M> void sendToServer(M message) {
        this.channel.sendToServer(message);
    }

    public <M> void sendTo(M message, Connection manager, NetworkDirection direction) {
        this.channel.sendTo(message, manager, direction);
    }

    public <M> void send(PacketDistributor.PacketTarget target, M message) {
        this.channel.send(target, message);
    }

    public <M> void reply(M message, NetworkEvent.Context context) {
        this.channel.reply(message, context);
    }
}

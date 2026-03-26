package com.blakebr0.cucumber.command;

import com.blakebr0.cucumber.Cucumber;
import com.blakebr0.cucumber.helper.BlockHelper;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.permissions.Permissions;
import net.minecraft.world.InteractionHand;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.transfer.access.ItemAccess;
import net.neoforged.neoforge.transfer.transaction.Transaction;

public final class ModCommands {
    public static final LiteralArgumentBuilder<CommandSourceStack> ROOT = Commands.literal(Cucumber.MOD_ID);

    @SubscribeEvent
    public void onRegisterCommands(RegisterCommandsEvent event) {
        var dispatcher = event.getDispatcher();

        dispatcher.register(ROOT.then(Commands.literal("fillenergy").requires(source -> source.permissions().hasPermission(Permissions.COMMANDS_ADMIN))
                .then(Commands.literal("block").executes(context -> {
                    var source = context.getSource();
                    var level = source.getLevel();
                    var player = source.getPlayerOrException();
                    var trace = BlockHelper.rayTraceBlocks(level, player);
                    var pos = trace.getBlockPos();

                    var energy = level.getCapability(Capabilities.Energy.BLOCK, pos, trace.getDirection());
                    if (energy != null) {
                        try (var transaction = Transaction.openRoot()) {
                            energy.insert(Integer.MAX_VALUE, transaction);
                            source.sendSuccess(() -> Component.translatable("message.cucumber.filled_energy", "block"), false);
                        }
                    } else {
                        source.sendFailure(Component.translatable("message.cucumber.filled_energy_error", "block"));
                    }

                    return 0;
                }))
                .then(Commands.literal("hand").executes(context -> {
                    var source = context.getSource();
                    var player = source.getPlayerOrException();
                    var stack = player.getItemInHand(InteractionHand.MAIN_HAND);

                    if (!stack.isEmpty()) {
                        var energy = ItemAccess.forStack(stack).getCapability(Capabilities.Energy.ITEM);

                        if (energy != null) {
                            try (var transaction = Transaction.openRoot()) {
                                energy.insert(Integer.MAX_VALUE, transaction);
                                source.sendSuccess(() -> Component.translatable("message.cucumber.filled_energy", "item"), false);
                            }
                        } else {
                            source.sendFailure(Component.translatable("message.cucumber.filled_energy_error", "item"));
                        }
                    } else {
                        source.sendFailure(Component.translatable("message.cucumber.filled_energy_error", "item"));
                    }

                    return 0;
                }))
        ));
    }
}

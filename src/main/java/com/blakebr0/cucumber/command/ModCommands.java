package com.blakebr0.cucumber.command;

import com.blakebr0.cucumber.Cucumber;
import com.blakebr0.cucumber.helper.BlockHelper;
import com.blakebr0.cucumber.util.Localizable;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public final class ModCommands {
    public static final LiteralArgumentBuilder<CommandSourceStack> ROOT = Commands.literal(Cucumber.MOD_ID);

    @SubscribeEvent
    public void onRegisterCommands(RegisterCommandsEvent event) {
        var dispatcher = event.getDispatcher();

        dispatcher.register(ROOT.then(Commands.literal("fillenergy").requires(source -> source.hasPermission(4)).executes(context -> {
            var world = context.getSource().getLevel();
            var player = context.getSource().getPlayerOrException();
            var trace = BlockHelper.rayTraceBlocks(world, player);
            var tile = world.getBlockEntity(trace.getBlockPos());

            if (tile != null) {
                var capability = tile.getCapability(CapabilityEnergy.ENERGY, trace.getDirection());

                if (capability.isPresent()) {
                    capability.ifPresent(energy -> {
                        if (energy.canReceive()) {
                            energy.receiveEnergy(Integer.MAX_VALUE, false);

                            var message = Localizable.of("message.cucumber.filled_energy").build();

                            context.getSource().sendSuccess(message, false);
                        }
                    });
                } else {
                    var message = Localizable.of("message.cucumber.filled_energy_error").build();

                    context.getSource().sendFailure(message);
                }
            } else {
                var message = Localizable.of("message.cucumber.filled_energy_error").build();

                context.getSource().sendFailure(message);
            }

            return 0;
        })));
    }
}

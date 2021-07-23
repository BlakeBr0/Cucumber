package com.blakebr0.cucumber.command;

import com.blakebr0.cucumber.Cucumber;
import com.blakebr0.cucumber.helper.BlockHelper;
import com.blakebr0.cucumber.util.Localizable;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public final class ModCommands {
    public static final LiteralArgumentBuilder<CommandSourceStack> ROOT = Commands.literal(Cucumber.MOD_ID);

    @SubscribeEvent
    public void onRegisterCommands(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();

        dispatcher.register(ROOT.then(Commands.literal("fillenergy").requires(source -> source.hasPermission(4)).executes(context -> {
            ServerLevel world = context.getSource().getLevel();
            ServerPlayer player = context.getSource().getPlayerOrException();

            BlockHitResult trace = BlockHelper.rayTraceBlocks(world, player);

            BlockEntity tile = world.getBlockEntity(trace.getBlockPos());
            if (tile != null) {
                LazyOptional<IEnergyStorage> capability = tile.getCapability(CapabilityEnergy.ENERGY, trace.getDirection());
                if (capability.isPresent()) {
                    capability.ifPresent(energy -> {
                        if (energy.canReceive()) {
                            energy.receiveEnergy(Integer.MAX_VALUE, false);
                            Component message = Localizable.of("message.cucumber.filled_energy").build();
                            context.getSource().sendSuccess(message, false);
                        }
                    });
                } else {
                    Component message = Localizable.of("message.cucumber.filled_energy_error").build();
                    context.getSource().sendFailure(message);
                }
            } else {
                Component message = Localizable.of("message.cucumber.filled_energy_error").build();
                context.getSource().sendFailure(message);
            }

            return 0;
        })));
    }
}

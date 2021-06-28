package com.blakebr0.cucumber.command;

import com.blakebr0.cucumber.Cucumber;
import com.blakebr0.cucumber.helper.BlockHelper;
import com.blakebr0.cucumber.util.Localizable;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public final class ModCommands {
    public static final LiteralArgumentBuilder<CommandSource> ROOT = Commands.literal(Cucumber.MOD_ID);

    @SubscribeEvent
    public void onRegisterCommands(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSource> dispatcher = event.getDispatcher();

        dispatcher.register(ROOT.then(Commands.literal("fillenergy").requires(source -> source.hasPermission(4)).executes(context -> {
            ServerWorld world = context.getSource().getLevel();
            ServerPlayerEntity player = context.getSource().getPlayerOrException();

            BlockRayTraceResult trace = BlockHelper.rayTraceBlocks(world, player);

            TileEntity tile = world.getBlockEntity(trace.getBlockPos());
            if (tile != null) {
                LazyOptional<IEnergyStorage> capability = tile.getCapability(CapabilityEnergy.ENERGY, trace.getDirection());
                if (capability.isPresent()) {
                    capability.ifPresent(energy -> {
                        if (energy.canReceive()) {
                            energy.receiveEnergy(Integer.MAX_VALUE, false);
                            ITextComponent message = Localizable.of("message.cucumber.filled_energy").build();
                            context.getSource().sendSuccess(message, false);
                        }
                    });
                } else {
                    ITextComponent message = Localizable.of("message.cucumber.filled_energy_error").build();
                    context.getSource().sendFailure(message);
                }
            } else {
                ITextComponent message = Localizable.of("message.cucumber.filled_energy_error").build();
                context.getSource().sendFailure(message);
            }

            return 0;
        })));
    }
}

package com.blakebr0.cucumber.command;

import com.blakebr0.cucumber.Cucumber;
import com.blakebr0.cucumber.util.Localizable;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public class ModCommands {
    public static final LiteralArgumentBuilder<CommandSource> ROOT = Commands.literal(Cucumber.MOD_ID);

    public static void onServerStarting(CommandDispatcher<CommandSource> dispatcher) {
        dispatcher.register(ROOT.then(Commands.literal("fillenergy").requires(source -> source.hasPermissionLevel(4)).executes(context -> {
            ServerWorld world = context.getSource().getWorld();
            ServerPlayerEntity player = context.getSource().asPlayer();

            float pitch = player.rotationPitch;
            float yaw = player.rotationYaw;
            Vector3d eyePos = player.getEyePosition(1.0F);
            float f2 = MathHelper.cos(-yaw * ((float) Math.PI / 180F) - (float) Math.PI);
            float f3 = MathHelper.sin(-yaw * ((float) Math.PI / 180F) - (float) Math.PI);
            float f4 = -MathHelper.cos(-pitch * ((float) Math.PI / 180F));
            float f5 = MathHelper.sin(-pitch * ((float) Math.PI / 180F));
            float f6 = f3 * f4;
            float f7 = f2 * f4;
            ModifiableAttributeInstance attribute = player.getAttribute(ForgeMod.REACH_DISTANCE.get());
            double d0 = attribute != null ? attribute.getValue() : 5.0D;
            Vector3d vec3d1 = eyePos.add((double) f6 * d0, (double) f5 * d0, (double) f7 * d0);
            BlockRayTraceResult trace = world.rayTraceBlocks(new RayTraceContext(eyePos, vec3d1, RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.NONE, player));

            TileEntity tile = world.getTileEntity(trace.getPos());
            if (tile != null) {
                LazyOptional<IEnergyStorage> capability = tile.getCapability(CapabilityEnergy.ENERGY, trace.getFace());
                if (capability.isPresent()) {
                    capability.ifPresent(energy -> {
                        if (energy.canReceive()) {
                            energy.receiveEnergy(Integer.MAX_VALUE, false);
                            ITextComponent message = Localizable.of("message.cucumber.filled_energy").build();
                            context.getSource().sendFeedback(message, true);
                        }
                    });
                } else {
                    ITextComponent message = Localizable.of("message.cucumber.filled_energy_error").build();
                    context.getSource().sendFeedback(message, true);
                }
            } else {
                ITextComponent message = Localizable.of("message.cucumber.filled_energy_error").build();
                context.getSource().sendFeedback(message, true);
            }

            return 0;
        })));
    }
}

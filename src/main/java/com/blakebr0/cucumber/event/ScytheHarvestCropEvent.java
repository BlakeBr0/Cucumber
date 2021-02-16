package com.blakebr0.cucumber.event;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

@Cancelable
public class ScytheHarvestCropEvent extends Event {
    private final IWorld world;
    private final BlockPos pos;
    private final BlockState state;
    private final ItemStack stack;
    private final PlayerEntity player;

    public ScytheHarvestCropEvent(IWorld world, BlockPos pos, BlockState state, ItemStack stack, PlayerEntity player) {
        this.pos = pos;
        this.world = world;
        this.state = state;
        this.stack = stack;
        this.player = player;
    }

    public IWorld getWorld()
    {
        return this.world;
    }

    public BlockPos getPos()
    {
        return this.pos;
    }

    public BlockState getState()
    {
        return this.state;
    }

    public ItemStack getItemStack() {
        return this.stack;
    }

    public PlayerEntity getPlayer() {
        return this.player;
    }
}

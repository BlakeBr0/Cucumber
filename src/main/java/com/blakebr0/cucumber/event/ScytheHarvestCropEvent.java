package com.blakebr0.cucumber.event;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

@Cancelable
public class ScytheHarvestCropEvent extends Event {
    private final LevelAccessor world;
    private final BlockPos pos;
    private final BlockState state;
    private final ItemStack stack;
    private final Player player;

    public ScytheHarvestCropEvent(LevelAccessor world, BlockPos pos, BlockState state, ItemStack stack, Player player) {
        this.pos = pos;
        this.world = world;
        this.state = state;
        this.stack = stack;
        this.player = player;
    }

    public LevelAccessor getWorld()
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

    public Player getPlayer() {
        return this.player;
    }
}

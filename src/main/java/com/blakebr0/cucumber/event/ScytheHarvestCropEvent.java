package com.blakebr0.cucumber.event;

import net.minecraft.block.BlockState;
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

    public ScytheHarvestCropEvent(IWorld world, BlockPos pos, BlockState state, ItemStack stack) {
        this.pos = pos;
        this.world = world;
        this.state = state;
        this.stack = stack;
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
}

package com.blakebr0.cucumber.fluid;

import com.blakebr0.cucumber.iface.IFluidHolder;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;

public class BaseFluidHolderItem extends FluidTank implements IFluidHandlerItem {
    private final IFluidHolder holder;
    private final ItemStack stack;

    public BaseFluidHolderItem(ItemStack stack, IFluidHolder holder) {
        super(holder.getCapacity(stack));
        this.stack = stack;
        this.holder = holder;
    }

    @Override
    public ItemStack getContainer() {
        return this.stack;
    }

    @Override
    public FluidStack getFluid() {
        return this.holder.getFluid(this.stack);
    }

    @Override
    public int fill(FluidStack resource, FluidAction action) {
        return this.holder.fill(this.stack, resource, action.execute());
    }

    @Override
    public FluidStack drain(FluidStack resource, FluidAction action) {
        return this.drain(resource.getAmount(), action);
    }

    @Override
    public FluidStack drain(int maxDrain, FluidAction action) {
        return this.holder.drain(this.stack, maxDrain, action.execute());
    }
}

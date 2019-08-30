package com.blakebr0.cucumber.fluid;

import com.blakebr0.cucumber.iface.IFluidHolder;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public class BaseFluidHolderItem extends FluidTank implements IFluidHandlerItem {
    private ItemStack stack;

    public BaseFluidHolderItem(ItemStack stack, IFluidHolder holder) {
        super(holder.getCapacity(stack));
        this.stack = stack;
    }

    @Override
    public ItemStack getContainer() {
        return this.stack;
    }
}

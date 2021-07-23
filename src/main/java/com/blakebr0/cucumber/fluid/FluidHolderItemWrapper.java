package com.blakebr0.cucumber.fluid;

import com.blakebr0.cucumber.iface.IFluidHolder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

public class FluidHolderItemWrapper implements ICapabilityProvider {
	private final ItemStack stack;
	private final IFluidHolder holder;

	public FluidHolderItemWrapper(ItemStack stack, IFluidHolder holder) {
		this.stack = stack;
		this.holder = holder;
	}

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> capability, Direction direction) {
		return CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY.orEmpty(capability, LazyOptional.of(() -> new BaseFluidHolderItem(this.stack, this.holder)));
	}
}

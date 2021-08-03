package com.blakebr0.cucumber.energy;

import com.blakebr0.cucumber.helper.NBTHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.energy.EnergyStorage;

/*
 * Based on RedstoneFlux ItemEnergyContainer
 * https://github.com/CoFH/RedstoneFlux/blob/master/src/main/java/cofh/redstoneflux/impl/ItemEnergyContainer.java
 */
public class ItemEnergyStorage extends EnergyStorage {
	private final ItemStack container;
	
	public ItemEnergyStorage(ItemStack stack, int capacity) {
		super(capacity);
		this.container = stack;
	}

	@Override
	public int receiveEnergy(int maxReceive, boolean simulate) {
		var energy = NBTHelper.getInt(this.container, "Energy");
		var energyReceived = Math.min(capacity - energy, Math.min(this.maxReceive, maxReceive));

		if (!simulate) {
			energy += energyReceived;
			NBTHelper.setInt(this.container, "Energy", energy);
		}

		return energyReceived;
	}

	@Override
	public int extractEnergy(int maxExtract, boolean simulate) {
		var energy = NBTHelper.getInt(this.container, "Energy");
		var energyExtracted = Math.min(energy, Math.min(this.maxExtract, maxExtract));

		if (!simulate) {
			energy -= energyExtracted;
			NBTHelper.setInt(this.container, "Energy", energy);
		}

		return energyExtracted;
	}

	@Override
	public int getEnergyStored() {
		return NBTHelper.getInt(this.container, "Energy");
	}

	@Override
	public int getMaxEnergyStored() {
		return this.capacity;
	}
	
	@Override
	public boolean canExtract() {
		return true;
	}
	
	@Override
	public boolean canReceive() {
		return true;
	}
}

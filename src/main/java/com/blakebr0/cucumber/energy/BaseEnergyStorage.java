package com.blakebr0.cucumber.energy;

import net.minecraftforge.energy.EnergyStorage;

// TODO: is this even necessary?
public class BaseEnergyStorage extends EnergyStorage {
	public BaseEnergyStorage(int capacity) {
		super(capacity);
	}

	public void setEnergyStored(int amount) {
		this.energy = amount;
	}
}

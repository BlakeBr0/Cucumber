package com.blakebr0.cucumber.energy;

import net.minecraftforge.energy.EnergyStorage;

public class BaseEnergyStorage extends EnergyStorage {
	public BaseEnergyStorage(int capacity) {
		super(capacity);
	}

	public void setEnergy(int amount) {
		this.energy = amount;
	}
}

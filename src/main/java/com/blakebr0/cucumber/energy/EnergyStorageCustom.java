package com.blakebr0.cucumber.energy;

import net.minecraftforge.energy.EnergyStorage;

public class EnergyStorageCustom extends EnergyStorage {
	public EnergyStorageCustom(int capacity) {
		super(capacity);
	}

	public void setEnergy(int amount) {
		this.energy = amount;
	}
}

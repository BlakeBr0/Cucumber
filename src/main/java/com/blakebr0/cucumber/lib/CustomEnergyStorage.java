package com.blakebr0.cucumber.lib;

import net.minecraftforge.energy.EnergyStorage;

@Deprecated
public class CustomEnergyStorage extends EnergyStorage {

	public CustomEnergyStorage(int capacity) {
		super(capacity);
	}

	public void setEnergy(int amount) {
		this.energy = amount;
	}
}

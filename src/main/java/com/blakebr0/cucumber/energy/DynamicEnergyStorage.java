package com.blakebr0.cucumber.energy;

public class DynamicEnergyStorage extends BaseEnergyStorage {
    private final int initialCapacity;

    public DynamicEnergyStorage(int capacity, Runnable onContentsChanged) {
        super(capacity, onContentsChanged);
        this.initialCapacity = capacity;
    }

    public void setMaxEnergyStorage(int capacity) {
        this.capacity = capacity;
    }

    public void resetMaxEnergyStorage() {
        this.capacity = this.initialCapacity;
    }
}

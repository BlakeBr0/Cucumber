package com.blakebr0.cucumber.energy;

import net.neoforged.neoforge.transfer.energy.SimpleEnergyHandler;

import java.util.function.Consumer;

public class CEnergyStorage extends SimpleEnergyHandler {
    private final Consumer<Integer> onContentsChanged;
    private final int initialCapacity;

    public CEnergyStorage(int capacity, Consumer<Integer> onContentsChanged) {
        this(capacity, capacity, capacity, onContentsChanged);
    }

    public CEnergyStorage(int capacity, int maxReceive, int maxExtract, Consumer<Integer> onContentsChanged) {
        this(capacity, maxReceive, maxExtract, 0, onContentsChanged);
    }

    public CEnergyStorage(int capacity, int maxReceive, int maxExtract, int energy, Consumer<Integer> onContentsChanged) {
        super(capacity, maxReceive, maxExtract, energy);
        this.onContentsChanged = onContentsChanged;
        this.initialCapacity = capacity;
    }

    @Override
    protected void onEnergyChanged(int previousAmount) {
        if (this.onContentsChanged != null) {
            this.onContentsChanged.accept(previousAmount);
        }
    }

    public void setMaxCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setMaxCapacity(double capacity) {
        this.capacity = (int) capacity;
    }

    public void resetMaxCapacity() {
        this.capacity = this.initialCapacity;
    }
}

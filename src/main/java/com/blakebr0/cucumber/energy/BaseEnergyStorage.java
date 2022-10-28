package com.blakebr0.cucumber.energy;

import net.minecraftforge.energy.EnergyStorage;

public class BaseEnergyStorage extends EnergyStorage {
    private final Runnable onContentsChanged;

    public BaseEnergyStorage(int capacity, Runnable onContentsChanged) {
        this(capacity, capacity, capacity, onContentsChanged);
    }

    public BaseEnergyStorage(int capacity, int maxReceive, int maxExtract, Runnable onContentsChanged) {
        this(capacity, maxReceive, maxExtract, 0, onContentsChanged);
    }

    public BaseEnergyStorage(int capacity, int maxReceive, int maxExtract, int energy, Runnable onContentsChanged) {
        super(capacity, maxReceive, maxExtract, energy);
        this.onContentsChanged = onContentsChanged;
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        var received = super.receiveEnergy(maxReceive, simulate);

        if (!simulate && received != 0 && this.onContentsChanged != null)
            this.onContentsChanged.run();

        return received;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        var extracted = super.extractEnergy(maxExtract, simulate);

        if (!simulate && extracted != 0 && this.onContentsChanged != null)
            this.onContentsChanged.run();

        return extracted;
    }

    public void setEnergyStored(int energy) {
        this.energy = energy;

        if (this.onContentsChanged != null)
            this.onContentsChanged.run();
    }
}

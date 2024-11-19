package com.blakebr0.cucumber.inventory;

import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SidedInventoryWrapper implements IItemHandler {
    private final BaseItemStackHandler inventory;
    private final Direction direction;
    private final CanInsertFunction.Sided canInsert;
    private final CanExtractFunction.Sided canExtract;

    private SidedInventoryWrapper(BaseItemStackHandler inventory, Direction direction, @Nullable CanInsertFunction.Sided canInsert, @Nullable CanExtractFunction.Sided canExtract) {
        this.inventory = inventory;
        this.direction = direction;
        this.canInsert = canInsert;
        this.canExtract = canExtract;
    }

    @Override
    public int getSlots() {
        return this.inventory.getSlots();
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return this.inventory.getStackInSlot(slot);
    }

    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
        if (this.canInsert != null && !this.canInsert.apply(slot, stack, this.direction))
            return stack;

        return this.inventory.insertItem(slot, stack, simulate);
    }

    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        if (this.canExtract != null && !this.canExtract.apply(slot, this.direction))
            return ItemStack.EMPTY;

        return this.inventory.extractItem(slot, amount, simulate);
    }

    @Override
    public int getSlotLimit(int slot) {
        return this.inventory.getSlotLimit(slot);
    }

    @Override
    public boolean isItemValid(int slot, ItemStack stack) {
        return this.canInsert != null && this.canInsert.apply(slot, stack, this.direction) && this.inventory.isItemValid(slot, stack);
    }

    public static SidedInventoryWrapper[] create(BaseItemStackHandler inventory, List<Direction> directions, CanInsertFunction.Sided canInsert, CanExtractFunction.Sided canExtract) {
        var wrappers = new SidedInventoryWrapper[directions.size()];

        for (var i = 0; i < directions.size(); i++) {
            wrappers[i] = new SidedInventoryWrapper(inventory, directions.get(i), canInsert, canExtract);
        }

        return wrappers;
    }
}

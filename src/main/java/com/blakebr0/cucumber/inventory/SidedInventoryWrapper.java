package com.blakebr0.cucumber.inventory;

import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.transfer.TransferPreconditions;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.item.ItemStacksResourceHandler;
import net.neoforged.neoforge.transfer.transaction.TransactionContext;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class SidedInventoryWrapper extends ItemStacksResourceHandler {
    private final CItemStacksHandler inventory;
    private final Direction direction;
    private final CanInsertFunction.Sided canInsert;
    private final CanExtractFunction.Sided canExtract;

    private SidedInventoryWrapper(CItemStacksHandler inventory, Direction direction, @Nullable CanInsertFunction.Sided canInsert, @Nullable CanExtractFunction.Sided canExtract) {
        super(inventory.getStacks());
        this.inventory = inventory;
        this.direction = direction;
        this.canInsert = canInsert;
        this.canExtract = canExtract;
    }

    @Override
    public int insert(int index, ItemResource resource, int amount, TransactionContext transaction) {
        if (this.canInsert != null && !this.canInsert.apply(index, resource, this.direction))
            return 0;

        return this.inventory.insert(index, resource, amount, transaction);
    }

    @Override
    public int extract(int index, ItemResource resource, int amount, TransactionContext transaction) {
        if (this.canExtract != null && !this.canExtract.apply(index, this.direction))
            return 0;

        return this.inventory.extract(index, resource, amount, transaction);
    }

    @Override
    public void set(int index, ItemResource resource, int amount) {
        TransferPreconditions.checkNonNegative(amount);
        if (resource.isEmpty() && amount > 0) {
            throw new IllegalArgumentException("Resource is empty but the amount is positive: " + amount);
        }

        var oldContents = this.inventory.getStacks().set(index, getStackFrom(resource, amount));
        onContentsChanged(index, oldContents);
    }

    @Override
    public int size() {
        return this.inventory.size();
    }

    @Override
    public ItemResource getResource(int index) {
        return this.inventory.getResource(index);
    }

    @Override
    public long getAmountAsLong(int index) {
        Objects.checkIndex(index, size());
        return getAmountFrom(this.inventory.getStacks().get(index));
    }

    @Override
    public boolean isValid(int index, ItemResource resource) {
        return this.inventory.isValid(index, resource);
    }

    @Override
    public void serialize(ValueOutput output) {
        this.inventory.serialize(output);
    }

    @Override
    public void deserialize(ValueInput input) {
        this.inventory.deserialize(input);
    }

    @Override
    public NonNullList<ItemStack> copyToList() {
        return NonNullList.of(this.emptyStack, this.inventory.getStacks().toArray(new ItemStack[0]));
    }

    public int getSlotLimit(int slot) {
        return this.inventory.getSlotLimit(slot);
    }

    public static SidedInventoryWrapper[] create(CItemStacksHandler inventory, List<Direction> directions, CanInsertFunction.Sided canInsert, CanExtractFunction.Sided canExtract) {
        var wrappers = new SidedInventoryWrapper[directions.size()];

        for (var i = 0; i < directions.size(); i++) {
            wrappers[i] = new SidedInventoryWrapper(inventory, directions.get(i), canInsert, canExtract);
        }

        return wrappers;
    }
}

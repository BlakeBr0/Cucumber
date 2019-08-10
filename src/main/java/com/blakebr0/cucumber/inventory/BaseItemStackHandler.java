package com.blakebr0.cucumber.inventory;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.items.ItemStackHandler;
import org.apache.commons.lang3.ArrayUtils;

import java.util.function.BiFunction;

public class BaseItemStackHandler extends ItemStackHandler {
    private final Runnable onContentsChanged;
    private BiFunction<Integer, ItemStack, Boolean> slotValidator = null;
    private int maxStackSize = 64;
    private int[] outputSlots = null;

    public BaseItemStackHandler(int size) {
        this(size, null);
    }

    public BaseItemStackHandler(int size, Runnable onContentsChanged) {
        super(size);
        this.onContentsChanged = onContentsChanged;
    }

    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
        if (this.outputSlots != null && ArrayUtils.contains(this.outputSlots, slot))
            return stack;
        return super.insertItem(slot, stack, simulate);
    }

    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        if (this.outputSlots != null && !ArrayUtils.contains(this.outputSlots, slot))
            return ItemStack.EMPTY;
        return super.extractItem(slot, amount, simulate);
    }

    @Override
    public int getSlotLimit(int slot) {
        return this.maxStackSize;
    }

    @Override
    public boolean isItemValid(int slot, ItemStack stack) {
        return this.slotValidator == null || this.slotValidator.apply(slot, stack);
    }

    @Override
    protected void onContentsChanged(int slot) {
        if (this.onContentsChanged != null)
            this.onContentsChanged.run();
    }

    public NonNullList<ItemStack> getStacks() {
        return this.stacks;
    }

    public BaseItemStackHandler setSlotLimit(int size) {
        this.maxStackSize = size;
        return this;
    }

    public BaseItemStackHandler setSlotValidator(BiFunction<Integer, ItemStack, Boolean> validator) {
        this.slotValidator = validator;
        return this;
    }

    public BaseItemStackHandler setOutputSlots(int... slots) {
        this.outputSlots = slots;
        return this;
    }
}

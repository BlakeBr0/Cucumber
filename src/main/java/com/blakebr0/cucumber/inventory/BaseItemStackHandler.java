package com.blakebr0.cucumber.inventory;

import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import org.apache.commons.lang3.ArrayUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public class BaseItemStackHandler extends ItemStackHandler {
    private final Runnable onContentsChanged;
    private final Map<Integer, Integer> slotSizeMap;
    private BiFunction<Integer, ItemStack, Boolean> canInsert = null;
    private Function<Integer, Boolean> canExtract = null;
    private int maxStackSize = 64;
    private int[] outputSlots = null;

    protected BaseItemStackHandler(int size, Runnable onContentsChanged) {
        super(size);
        this.onContentsChanged = onContentsChanged;
        this.slotSizeMap = new HashMap<>();
    }

    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
        return this.insertItem(slot, stack, simulate, false);
    }

    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate, boolean container) {
        if (!container && this.outputSlots != null && ArrayUtils.contains(this.outputSlots, slot))
            return stack;

        return super.insertItem(slot, stack, simulate);
    }

    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        return this.extractItem(slot, amount, simulate, false);
    }

    public ItemStack extractItem(int slot, int amount, boolean simulate, boolean container) {
        if (!container) {
            if (this.canExtract != null && !this.canExtract.apply(slot))
                return ItemStack.EMPTY;

            if (this.outputSlots != null && !ArrayUtils.contains(this.outputSlots, slot))
                return ItemStack.EMPTY;
        }

        return super.extractItem(slot, amount, simulate);
    }

    @Override
    public int getSlotLimit(int slot) {
        return this.slotSizeMap.containsKey(slot) ? this.slotSizeMap.get(slot) : this.maxStackSize;
    }

    @Override
    public boolean isItemValid(int slot, ItemStack stack) {
        return this.canInsert == null || this.canInsert.apply(slot, stack);
    }

    @Override
    protected void onContentsChanged(int slot) {
        if (this.onContentsChanged != null)
            this.onContentsChanged.run();
    }

    public NonNullList<ItemStack> getStacks() {
        return this.stacks;
    }

    public int[] getOutputSlots() {
        return this.outputSlots;
    }

    public void setDefaultSlotLimit(int size) {
        this.maxStackSize = size;
    }

    public void addSlotLimit(int slot, int size) {
        this.slotSizeMap.put(slot, size);
    }

    public void setCanInsert(BiFunction<Integer, ItemStack, Boolean> validator) {
        this.canInsert = validator;
    }

    public void setCanExtract(Function<Integer, Boolean> canExtract) {
        this.canExtract = canExtract;
    }

    public void setOutputSlots(int... slots) {
        this.outputSlots = slots;
    }

    public Container toIInventory() {
        return new SimpleContainer(this.stacks.toArray(new ItemStack[0]));
    }

    /**
     * Creates a deep copy of this BaseItemStackHandler, including new copies of the items
     * @return the copy of this BaseItemStackHandler
     */
    public BaseItemStackHandler copy() {
        var newInventory = new BaseItemStackHandler(this.getSlots(), this.onContentsChanged);

        newInventory.setDefaultSlotLimit(this.maxStackSize);
        newInventory.setCanInsert(this.canInsert);
        newInventory.setCanExtract(this.canExtract);
        newInventory.setOutputSlots(this.outputSlots);

        this.slotSizeMap.forEach(newInventory::addSlotLimit);

        for (int i = 0; i < this.getSlots(); i++) {
            var stack = this.getStackInSlot(i);

            newInventory.setStackInSlot(i, stack.copy());
        }

        return newInventory;
    }

    public static BaseItemStackHandler create(int size) {
        return create(size, builder -> {});
    }

    public static BaseItemStackHandler create(int size, Runnable onContentsChanged) {
        return create(size, onContentsChanged, builder -> {});
    }

    public static BaseItemStackHandler create(int size, Consumer<BaseItemStackHandler> builder) {
        return create(size, null, builder);
    }

    public static BaseItemStackHandler create(int size, Runnable onContentsChanged, Consumer<BaseItemStackHandler> builder) {
        var handler = new BaseItemStackHandler(size, onContentsChanged);
        builder.accept(handler);
        return handler;
    }
}

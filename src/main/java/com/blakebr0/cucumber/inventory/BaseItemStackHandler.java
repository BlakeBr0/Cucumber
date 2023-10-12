package com.blakebr0.cucumber.inventory;

import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import org.apache.commons.lang3.ArrayUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public class BaseItemStackHandler extends ItemStackHandler {
    private final Runnable onContentsChanged;
    private final Map<Integer, Integer> slotSizeMap;
    private final RecipeWrapper recipeWrapper;
    private BiFunction<Integer, ItemStack, Boolean> canInsert = null;
    private Function<Integer, Boolean> canExtract = null;
    private int maxStackSize = 64;
    private int[] outputSlots = null;

    protected BaseItemStackHandler(int size, Runnable onContentsChanged) {
        super(size);
        this.onContentsChanged = onContentsChanged;
        this.slotSizeMap = new HashMap<>();
        this.recipeWrapper = new RecipeWrapper(this);
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

    @Override // copied from ItemStackHandler#serializeNBT
    public CompoundTag serializeNBT() {
        var items = new ListTag();

        for (int i = 0; i < this.stacks.size(); i++) {
            var stack = this.stacks.get(i);

            if (!stack.isEmpty()) {
                var item = new CompoundTag();

                item.putInt("Slot", i);

                // change: store additional ExtendedCount value for stack sizes larger than normal
                if (stack.getCount() > 64) {
                    item.putInt("ExtendedCount", stack.getCount());
                }

                stack.save(item);
                items.add(item);
            }
        }

        var nbt = new CompoundTag();

        nbt.put("Items", items);
        nbt.putInt("Size", this.stacks.size());

        return nbt;
    }

    @Override // copied from ItemStackHandler#deserializeNBT
    public void deserializeNBT(CompoundTag nbt) {
        this.setSize(nbt.contains("Size", 3) ? nbt.getInt("Size") : this.stacks.size());

        var items = nbt.getList("Items", 10);

        for (int i = 0; i < items.size(); ++i) {
            var item = items.getCompound(i);
            int slot = item.getInt("Slot");

            if (slot >= 0 && slot < this.stacks.size()) {
                var stack = ItemStack.of(item);

                // change: use the ExtendedCount value instead if it exists
                if (item.contains("ExtendedCount")) {
                    stack.setCount(item.getInt("ExtendedCount"));
                }

                this.stacks.set(slot, stack);
            }
        }

        this.onLoad();
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

    /**
     * Adds a new slot limit for the specified slot
     * <br>
     * If the size is above 64, it must be a multiple of 64
     */
    public void addSlotLimit(int slot, int size) {
        if (size > 64 && size % 64 != 0) {
            throw new IllegalArgumentException("Slot limits above 64 must be a multiple of 64");
        }

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

    public RecipeWrapper asRecipeWrapper() {
        return this.recipeWrapper;
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

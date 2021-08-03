package com.blakebr0.cucumber.inventory;

import com.blakebr0.cucumber.util.TriFunction;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandlerModifiable;

import java.util.function.BiFunction;

public record SidedItemStackHandlerWrapper(BaseItemStackHandler inventory,
                                           Direction direction,
                                           TriFunction<Integer, ItemStack, Direction, Boolean> canInsert,
                                           BiFunction<Integer, Direction, Boolean> canExtract) implements IItemHandlerModifiable {

    @Override
    public void setStackInSlot(int slot, ItemStack stack) {
        this.inventory.setStackInSlot(slot, stack);
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
        if (stack.isEmpty())
            return ItemStack.EMPTY;

        if (!this.isItemValid(slot, stack))
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
        return this.canInsert == null || this.canInsert.apply(slot, stack, this.direction);
    }

    @SuppressWarnings("unchecked")
    public static LazyOptional<IItemHandlerModifiable>[] create(BaseItemStackHandler inv, Direction[] sides, TriFunction<Integer, ItemStack, Direction, Boolean> canInsert, BiFunction<Integer, Direction, Boolean> canExtract) {
        LazyOptional<IItemHandlerModifiable>[] ret = new LazyOptional[sides.length];
        for (int x = 0; x < sides.length; x++) {
            final var side = sides[x];
            ret[x] = LazyOptional.of(() -> new SidedItemStackHandlerWrapper(inv, side, canInsert, canExtract));
        }

        return ret;
    }
}

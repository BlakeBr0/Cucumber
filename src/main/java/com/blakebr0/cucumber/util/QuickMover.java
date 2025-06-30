package com.blakebr0.cucumber.util;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * After years of struggling with configuring shift-clicking in container menus, it's time to make a more readable (to me)
 * abstraction for handling this.
 */
public class QuickMover {
    private final MoveFunction moveFunc;
    private final List<MoveAttempt> attempts;

    /**
     * The current minimum slot for any added attempts to run
     */
    private int after = -1;

    public QuickMover(MoveFunction moveFunc) {
        this.moveFunc = moveFunc;
        this.attempts = new ArrayList<>();
    }

    public QuickMover add(MoveCondition condition, int start, int slots) {
        this.attempts.add(new MoveAttempt(condition, this.after, start, slots));
        return this;
    }

    public QuickMover fallback(int start, int slots) {
        this.attempts.add(new MoveAttempt((slot, stack, player) -> true, -1, start, slots));
        return this;
    }

    /**
     * Set the slot requirement for subsequently registered attempts
     */
    public QuickMover after(int slot) {
        this.after = slot;
        return this;
    }

    public boolean run(int slot, ItemStack stack, Player player) {
        for (var attempt : this.attempts) {
            if (slot >= attempt.minSlot && attempt.condition.apply(slot, stack, player)) {
                if (!this.moveFunc.apply(stack, attempt.start, attempt.end(), attempt.inverse)) {
                    return false;
                }
            }
        }

        return true;
    }

    /** {@link net.minecraft.world.inventory.AbstractContainerMenu#moveItemStackTo(ItemStack, int, int, boolean)} */
    @SuppressWarnings("JavadocReference")
    @FunctionalInterface
    public interface MoveFunction {
        boolean apply(ItemStack stack, int startIndex, int endIndex, boolean reverseDirection);
    }

    @FunctionalInterface
    public interface MoveCondition {
        boolean apply(int slot, ItemStack stack, Player player);
    }

    private record MoveAttempt(MoveCondition condition, int minSlot, int start, int slots, boolean inverse) {
        MoveAttempt(MoveCondition condition, int minSlot, int start, int slots) {
            this(condition, minSlot, start, slots, false);
        }

        public int end() {
            return this.start + this.slots;
        }
    }
}

package com.blakebr0.cucumber.inventory;

import net.minecraft.core.Direction;
import net.neoforged.neoforge.transfer.item.ItemResource;

@FunctionalInterface
public interface CanInsertFunction {
    boolean apply(int slot, ItemResource resource);

    @FunctionalInterface
    interface Sided {
        boolean apply(int slot, ItemResource resource, Direction direction);
    }
}

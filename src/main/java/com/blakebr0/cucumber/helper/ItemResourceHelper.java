package com.blakebr0.cucumber.helper;

import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.item.ItemStacksResourceHandler;
import net.neoforged.neoforge.transfer.transaction.Transaction;

public final class ItemResourceHelper {
    public static boolean canCombine(ItemStacksResourceHandler handler, int slot, ItemStack stack) {
        try (var tx = Transaction.openRoot()) {
            return handler.insert(slot, ItemResource.of(stack), stack.count(), tx) == stack.count();
        }
    }
}

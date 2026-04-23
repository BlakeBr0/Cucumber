package com.blakebr0.cucumber.helper;

import com.blakebr0.cucumber.inventory.CItemStacksHandler;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.transaction.Transaction;

public final class ItemResourceHelper {
    public static boolean canCombine(CItemStacksHandler handler, int slot, ItemStack stack) {
        try (var tx = Transaction.openRoot()) {
            return handler.insert(slot, ItemResource.of(stack), stack.count(), tx, true) == stack.count();
        }
    }
}

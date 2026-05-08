package com.blakebr0.cucumber.inventory.slot;

import com.blakebr0.cucumber.inventory.CItemStacksHandler;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.item.ResourceHandlerSlot;
import net.neoforged.neoforge.transfer.transaction.Transaction;

public class CSlot extends ResourceHandlerSlot {
    private final CItemStacksHandler inventory;
    private final int index;

    public CSlot(CItemStacksHandler inventory, int index, int x, int y) {
        super(inventory, inventory::set, index, x, y);
        this.inventory = inventory;
        this.index = index;
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        if (stack.isEmpty())
            return false;

        return this.inventory.checkCanInsert(this.index, ItemResource.of(stack));
    }

    @Override
    public boolean mayPickup(Player player) {
        var resource = this.inventory.getResource(this.index);
        if (resource.isEmpty())
            return false;

        try (var tx = Transaction.openRoot()) {
            return this.inventory.extract(this.index, resource, 1, tx, true) == 1;
        }
    }

    @Override
    public int getMaxStackSize(ItemStack stack) {
        var slotLimit = this.inventory.getSlotLimit(this.index);
        if (slotLimit > 64) {
            // if the max size for the stack is less than 64, then we should decrease the max stack size to the
            // same ratio
            if (stack.getMaxStackSize() < 64) {
                return (int) (slotLimit * (float) stack.getMaxStackSize() / 64);
            }

            return slotLimit;
        }

        return super.getMaxStackSize(stack);
    }
}

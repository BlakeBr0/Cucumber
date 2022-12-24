package com.blakebr0.cucumber.inventory.slot;

import com.blakebr0.cucumber.inventory.BaseItemStackHandler;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

public class BaseItemStackHandlerSlot extends SlotItemHandler {
    private final BaseItemStackHandler inventory;
    private final int index;

    public BaseItemStackHandlerSlot(BaseItemStackHandler inventory, int index, int x, int y) {
        super(inventory, index, x, y);
        this.inventory = inventory;
        this.index = index;
    }

    @Override
    public boolean mayPickup(Player player) {
        return !this.inventory.extractItem(this.index, 1, true, true).isEmpty();
    }

    @Override
    public ItemStack remove(int amount) {
        return this.inventory.extractItem(this.index, amount, false, true);
    }
}

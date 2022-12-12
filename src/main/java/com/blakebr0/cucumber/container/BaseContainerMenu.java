package com.blakebr0.cucumber.container;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;

public class BaseContainerMenu extends AbstractContainerMenu {
    private final BlockPos pos;

    protected BaseContainerMenu(MenuType<?> menu, int id, BlockPos pos) {
        super(menu, id);
        this.pos = pos;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int slot) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player) {
        return player.distanceToSqr(this.pos.getX() + 0.5, this.pos.getY() + 0.5, this.pos.getZ() + 0.5) <= 64;
    }

    public BlockPos getBlockPos() {
        return this.pos;
    }
}

package com.blakebr0.cucumber.tileentity;

import com.blakebr0.cucumber.inventory.BaseItemStackHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Clearable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class BaseInventoryTileEntity extends BaseTileEntity implements Clearable {
    public BaseInventoryTileEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public abstract BaseItemStackHandler getInventory();

    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider lookup) {
        super.loadAdditional(tag, lookup);
        this.getInventory().deserializeNBT(lookup, tag);
    }

    @Override
    public void saveAdditional(CompoundTag tag, HolderLookup.Provider lookup) {
        tag.merge(this.getInventory().serializeNBT(lookup));
    }

    public boolean isUsableByPlayer(Player player) {
        var pos = this.getBlockPos();
        return player.distanceToSqr(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5) <= 64;
    }

    @Override
    public void clearContent() {
        this.getInventory().getStacks().clear();
    }
}

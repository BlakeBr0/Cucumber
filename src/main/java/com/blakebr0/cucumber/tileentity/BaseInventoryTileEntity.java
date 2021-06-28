package com.blakebr0.cucumber.tileentity;

import com.blakebr0.cucumber.inventory.BaseItemStackHandler;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public abstract class BaseInventoryTileEntity extends BaseTileEntity {
    private final LazyOptional<IItemHandler> capability = LazyOptional.of(this::getInventory);

    public BaseInventoryTileEntity(TileEntityType<?> type) {
        super(type);
    }

    public abstract BaseItemStackHandler getInventory();

    @Override
    public void load(BlockState state, CompoundNBT tag) {
        super.load(state, tag);
        this.getInventory().deserializeNBT(tag);
    }

    @Override
    public CompoundNBT save(CompoundNBT tag) {
        super.save(tag);
        tag.merge(this.getInventory().serializeNBT());
        return tag;
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        if (!this.isRemoved() && cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.orEmpty(cap, this.capability);
        }

        return super.getCapability(cap, side);
    }

    public boolean isUsableByPlayer(PlayerEntity player) {
        BlockPos pos = this.getBlockPos();
        return player.distanceToSqr(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5) <= 64;
    }
}

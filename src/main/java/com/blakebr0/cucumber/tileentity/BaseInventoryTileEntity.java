package com.blakebr0.cucumber.tileentity;

import com.blakebr0.cucumber.inventory.BaseItemStackHandler;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;

public abstract class BaseInventoryTileEntity extends BaseTileEntity {
    public BaseInventoryTileEntity(TileEntityType<?> type) {
        super(type);
    }

    public abstract BaseItemStackHandler getInventory();

    @Override
    public void read(CompoundNBT tag) {
        super.read(tag);
        this.getInventory().deserializeNBT(tag);
    }

    @Override
    public CompoundNBT write(CompoundNBT tag) {
        super.write(tag);
        tag.merge(this.getInventory().serializeNBT());
        return tag;
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        if (!this.removed && cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.orEmpty(cap, LazyOptional.of(this::getInventory));
        }

        return super.getCapability(cap, side);
    }
}

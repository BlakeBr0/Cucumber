package com.blakebr0.cucumber.tileentity;

import com.blakebr0.cucumber.helper.TileEntityHelper;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

public class BaseTileEntity extends TileEntity {
	public BaseTileEntity(TileEntityType<?> type) {
		super(type);
	}

	@Override
	public SUpdateTileEntityPacket getUpdatePacket() {
		return new SUpdateTileEntityPacket(this.getBlockPos(), -1, this.getUpdateTag());
	}

	@Override
	public void onDataPacket(NetworkManager manager, SUpdateTileEntityPacket packet) {
		this.load(this.getBlockState(), packet.getTag());
	}

	@Override
	public final CompoundNBT getUpdateTag() {
		return this.save(new CompoundNBT());
	}

	public void markDirtyAndDispatch() {
		super.setChanged();
		TileEntityHelper.dispatchToNearbyPlayers(this);
	}
}

package com.blakebr0.cucumber.tileentity;

import com.blakebr0.cucumber.util.VanillaPacketDispatcher;
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
		return new SUpdateTileEntityPacket(this.getPos(), -1, this.getUpdateTag());
	}

	@Override
	public void onDataPacket(NetworkManager manager, SUpdateTileEntityPacket packet) {
		this.read(packet.getNbtCompound());
	}

	@Override
	public final CompoundNBT getUpdateTag() {
		return this.write(new CompoundNBT());
	}

	public void read(CompoundNBT tag) {
		this.read(this.getBlockState(), tag);
	}

	public void markDirtyAndDispatch() {
		super.markDirty();
		VanillaPacketDispatcher.dispatchTEToNearbyPlayers(this);
	}
}

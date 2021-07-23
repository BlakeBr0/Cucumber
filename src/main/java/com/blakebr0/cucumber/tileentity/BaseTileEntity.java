package com.blakebr0.cucumber.tileentity;

import com.blakebr0.cucumber.helper.TileEntityHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class BaseTileEntity extends BlockEntity {
	public BaseTileEntity(BlockEntityType<?> type) {
		super(type);
	}

	@Override
	public ClientboundBlockEntityDataPacket getUpdatePacket() {
		return new ClientboundBlockEntityDataPacket(this.getBlockPos(), -1, this.getUpdateTag());
	}

	@Override
	public void onDataPacket(Connection manager, ClientboundBlockEntityDataPacket packet) {
		this.load(this.getBlockState(), packet.getTag());
	}

	@Override
	public final CompoundTag getUpdateTag() {
		return this.save(new CompoundTag());
	}

	public void markDirtyAndDispatch() {
		super.setChanged();
		TileEntityHelper.dispatchToNearbyPlayers(this);
	}
}

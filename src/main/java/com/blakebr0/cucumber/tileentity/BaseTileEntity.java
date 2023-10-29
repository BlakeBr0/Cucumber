package com.blakebr0.cucumber.tileentity;

import com.blakebr0.cucumber.helper.TileEntityHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class BaseTileEntity extends BlockEntity {
	private boolean isChanged = false;

	public BaseTileEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}

	@Override
	public ClientboundBlockEntityDataPacket getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this, BlockEntity::saveWithFullMetadata);
	}

	@Override
	public CompoundTag getUpdateTag() {
		return this.saveWithFullMetadata();
	}

	@Deprecated(forRemoval = true)
	public void markDirtyAndDispatch() {
		this.setChangedAndDispatch();
	}

	public void setChanged() {
		super.setChanged();
		this.isChanged = true;
	}

	public void setChangedFast() {
		if (this.level != null) {
			this.level.blockEntityChanged(this.getBlockPos());
			this.isChanged = true;
		}
	}

	public void setChangedAndDispatch() {
		this.setChanged();
		TileEntityHelper.dispatchToNearbyPlayers(this);
		this.isChanged = false;
	}

	public void dispatchIfChanged() {
		if (this.isChanged) {
			TileEntityHelper.dispatchToNearbyPlayers(this);
			this.isChanged = false;
		}
	}
}

package com.blakebr0.cucumber.inventory;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
// TODO: Figure out new container stuff
public class EmptyContainer extends Container {
	protected EmptyContainer(ContainerType<?> p_i50105_1_, int p_i50105_2_) {
		super(p_i50105_1_, p_i50105_2_);
	}

	@Override
	public boolean canInteractWith(PlayerEntity player) {
		return true;
	}
}

package com.blakebr0.cucumber.iface;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

public interface ICustomBow {
	default float getDrawSpeedMulti(ItemStack stack) {
		return 1.0F;
	}

	static ItemStack findAmmo(PlayerEntity player) {
		if (isArrow(player.getHeldItem(Hand.OFF_HAND))) {
			return player.getHeldItem(Hand.OFF_HAND);
		} else if (isArrow(player.getHeldItem(Hand.MAIN_HAND))) {
			return player.getHeldItem(Hand.MAIN_HAND);
		} else {
			for (int i = 0; i < player.field_71071_by.getSizeInventory(); i++) {
				ItemStack stack = player.field_71071_by.getStackInSlot(i);
				if (isArrow(stack)) {
					return stack;
				}
			}

			return ItemStack.EMPTY;
		}
	}

	static boolean isArrow(ItemStack stack) {
		return stack.getItem() instanceof ArrowItem;
	}
}

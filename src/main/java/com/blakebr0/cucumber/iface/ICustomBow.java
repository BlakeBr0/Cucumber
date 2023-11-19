package com.blakebr0.cucumber.iface;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;

public interface ICustomBow {
	default float getDrawSpeedMulti(ItemStack stack) {
		return 1.0F;
	}

	default float getBonusDamage(ItemStack stack) {
		return 0.0F;
	}

	boolean hasFOVChange();

	@Deprecated(forRemoval = true)
	static ItemStack findAmmo(Player player) {
		if (isArrow(player.getItemInHand(InteractionHand.OFF_HAND))) {
			return player.getItemInHand(InteractionHand.OFF_HAND);
		} else if (isArrow(player.getItemInHand(InteractionHand.MAIN_HAND))) {
			return player.getItemInHand(InteractionHand.MAIN_HAND);
		} else {
			for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
				var stack = player.getInventory().getItem(i);
				if (isArrow(stack)) {
					return stack;
				}
			}

			return ItemStack.EMPTY;
		}
	}

	@Deprecated(forRemoval = true)
	static boolean isArrow(ItemStack stack) {
		return stack.getItem() instanceof ArrowItem;
	}
}

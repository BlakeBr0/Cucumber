package com.blakebr0.cucumber.helper;

import java.util.*;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class StackHelper {

	public static ItemStack to(Object obj) {
		if (obj instanceof Block) {
			return new ItemStack((Block) obj);
		} else if (obj instanceof Item) {
			return new ItemStack((Item) obj);
		} else {
			return getNull();
		}
	}

	public static ItemStack to(Object obj, int amount) {
		if (obj instanceof Block) {
			return new ItemStack((Block) obj, amount, 0);
		} else if (obj instanceof Item) {
			return new ItemStack((Item) obj, amount, 0);
		} else {
			return getNull();
		}
	}

	public static ItemStack to(Object obj, int amount, int meta) {
		if (obj instanceof Block) {
			return new ItemStack((Block) obj, amount, meta);
		} else if (obj instanceof Item) {
			return new ItemStack((Item) obj, amount, meta);
		} else {
			return getNull();
		}
	}

	public static ItemStack withSize(ItemStack stack, int size, boolean container) {
		if (size <= 0) {
			if (container && stack.getItem().hasContainerItem(stack)) {
				return stack.getItem().getContainerItem(stack);
			} else {
				return getNull();
			}
		}
		stack.setCount(size);
		return stack;
	}

	public static ItemStack increase(ItemStack stack, int amount) {
		stack.grow(amount);
		return withSize(stack, stack.getCount(), false);
	}

	public static ItemStack decrease(ItemStack stack, int amount, boolean container) {
		if (isNull(stack)) {
			return null;
		}
		stack.shrink(amount);
		return withSize(stack, stack.getCount(), container);
	}

	public static int getPlaceFromList(List<ItemStack> list, ItemStack stack, boolean wildcard) {
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				if ((isNull(stack) && isNull(list.get(i))) || areItemsEqual(stack, list.get(i), wildcard)) {
					return i;
				}
			}
		}
		return -1;
	}

	public static ItemStack fromOre(String oreDict, int stackSize) {
		ItemStack item = ItemStack.EMPTY;
		List<ItemStack> list = OreDictionary.getOres(oreDict);
		if (!list.isEmpty()) {
			item = list.get(0).copy();
			{
				item.setCount(stackSize);
			}
		}
		return item;
	}

	public static boolean areItemsEqual(ItemStack stack1, ItemStack stack2, boolean wildcard) {
		return !isNull(stack1) && !isNull(stack2)
				&& (stack1.isItemEqual(stack2) || (wildcard && stack1.getItem() == stack2.getItem()
						&& (stack1.getItemDamage() == OreDictionary.WILDCARD_VALUE
								|| stack2.getItemDamage() == OreDictionary.WILDCARD_VALUE)));
	}

	public static boolean isNull(ItemStack stack) {
		return stack.isEmpty();
	}

	public static ItemStack getNull() {
		return ItemStack.EMPTY;
	}
}

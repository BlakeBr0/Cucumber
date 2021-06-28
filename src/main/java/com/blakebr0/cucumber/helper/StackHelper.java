package com.blakebr0.cucumber.helper;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTUtil;

import java.util.Set;

public final class StackHelper {
	public static ItemStack withSize(ItemStack stack, int size, boolean container) {
		if (size <= 0) {
			if (container && stack.hasContainerItem()) {
				return stack.getContainerItem();
			} else {
				return ItemStack.EMPTY;
			}
		}

		stack = stack.copy();
		stack.setCount(size);

		return stack;
	}

	public static ItemStack grow(ItemStack stack, int amount) {
		return withSize(stack, stack.getCount() + amount, false);
	}

	public static ItemStack shrink(ItemStack stack, int amount, boolean container) {
		if (stack.isEmpty()) return ItemStack.EMPTY;
		return withSize(stack, stack.getCount() - amount, container);
	}

	public static boolean areItemsEqual(ItemStack stack1, ItemStack stack2) {
		return !stack1.isEmpty() && !stack2.isEmpty() && stack1.sameItem(stack2);
	}
	
	public static boolean areStacksEqual(ItemStack stack1, ItemStack stack2) {
		return areItemsEqual(stack1, stack2) && ItemStack.tagMatches(stack1, stack2);
	}

	/**
	 * Checks if stack2 can be added to stack1
	 * @param stack1 the current stack
	 * @param stack2 the additional stack
	 * @return can these stacks be combined
	 */
	public static boolean canCombineStacks(ItemStack stack1, ItemStack stack2) {
		if (!stack1.isEmpty() && stack2.isEmpty()) return true;
		return areStacksEqual(stack1, stack2) && (stack1.getCount() + stack2.getCount()) <= stack1.getMaxStackSize();
	}

	/**
	 * Combines stack2 into stack1
	 * @param stack1 the current stack
	 * @param stack2 the additional stack
	 * @return the new combined stack
	 */
	public static ItemStack combineStacks(ItemStack stack1, ItemStack stack2) {
		if (stack1.isEmpty())
			return stack2.copy();

		return grow(stack1, stack2.getCount());
	}
	
	/**
	 * Compares the tags in stack1 to the corresponding tags in stack2
	 * @param stack1 the reference stack
	 * @param stack2 the actual stack
	 * @return all the corresponding tags are the same
	 */
	public static boolean compareTags(ItemStack stack1, ItemStack stack2) {
		if (!stack1.hasTag()) return true;
		if (stack1.hasTag() && !stack2.hasTag()) return false;
		
		Set<String> stack1Keys = NBTHelper.getTagCompound(stack1).getAllKeys();
		Set<String> stack2Keys = NBTHelper.getTagCompound(stack2).getAllKeys();
		
		for (String key : stack1Keys) {
			if (stack2Keys.contains(key)) {
				if (!NBTUtil.compareNbt(NBTHelper.getTag(stack1, key), NBTHelper.getTag(stack2, key), true)) {
					return false;
				}
			} else {
				return false;
			}
		}
		
		return true;
	}
}

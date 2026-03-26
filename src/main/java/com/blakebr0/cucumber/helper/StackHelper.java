package com.blakebr0.cucumber.helper;

import net.minecraft.world.item.ItemStack;

public final class StackHelper {
	public static ItemStack withSize(ItemStack stack, int size, boolean container) {
		if (size <= 0) {
			if (container) {
				var remainder = stack.getCraftingRemainder();
				if (remainder != null) {
					return remainder.create();
				}
			}

			return ItemStack.EMPTY;
		}

		stack = stack.copy();
		stack.setCount(size);

		return stack;
	}

	public static ItemStack grow(ItemStack stack, int amount) {
		return withSize(stack, stack.getCount() + amount, false);
	}

	public static ItemStack shrink(ItemStack stack, int amount, boolean container) {
		if (stack.isEmpty())
			return ItemStack.EMPTY;

		return withSize(stack, stack.getCount() - amount, container);
	}

	/**
	 * Shrinks the provided ItemStack and tries to add any container item to the result if possible
	 * @param stack the initial ItemStack
	 * @param amount the amount to shrink
	 * @return the shrunk ItemStack
	 */
	public static ItemStack shrinkAndRetainContainer(ItemStack stack, int amount) {
		if (stack.isEmpty())
			return ItemStack.EMPTY;

		var remaining = stack.getCraftingRemainder();
		var result = shrink(stack, amount, false);

		if (remaining != null && ItemStack.isSameItemSameComponents(stack, result)) {
			result.grow(Math.min(remaining.count(), result.getMaxStackSize()));
		}

		return result;
	}

	/**
	 * Inserts the maximum amount of stack2 into stack1 and returns the remaining item
	 * @param stack1 the current ItemStack
	 * @param stack2 the ItemStack to insert
	 * @return an {@link InsertResult} with the result ItemStack and the remaining ItemStack
	 */
	public static InsertResult insert(ItemStack stack1, ItemStack stack2) {
		if (stack1.isEmpty())
			return new InsertResult(stack2, ItemStack.EMPTY);

		if (!ItemStack.isSameItemSameComponents(stack1, stack2))
			return new InsertResult(stack1, stack2);

		var amount = Math.min(stack2.getCount(), stack1.getMaxStackSize() - stack1.getCount());

		return new InsertResult(grow(stack1, amount), shrink(stack2, amount, false));
	}

	/**
	 * Checks if stack1 can be added to stack2
	 * @param stack1 the new stack to add
	 * @param stack2 the current stack to add to
	 * @return can combine stacks
	 */
	public static boolean canCombineStacks(ItemStack stack1, ItemStack stack2) {
		if (!stack1.isEmpty() && stack2.isEmpty())
			return true;

		return ItemStack.isSameItemSameComponents(stack1, stack2) && (stack1.getCount() + stack2.getCount()) <= stack1.getMaxStackSize();
	}

	/**
	 * Combines stack2 into stack1
	 * @param stack1 the new stack to add
	 * @param stack2 the current stack to add to
	 * @return the new combined stack
	 */
	public static ItemStack combine(ItemStack stack1, ItemStack stack2) {
		if (stack2.isEmpty())
			return stack1.copy();

		return grow(stack2, stack1.getCount());
	}

	public record InsertResult(ItemStack result, ItemStack remainder) {}
}

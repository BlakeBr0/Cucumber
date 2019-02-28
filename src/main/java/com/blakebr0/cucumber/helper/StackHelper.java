package com.blakebr0.cucumber.helper;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.world.World;

import java.util.List;
import java.util.Set;

public class StackHelper {

	public static ItemStack withSize(ItemStack stack, int size, boolean container) {
		if (size <= 0) {
			if (container && stack.getItem().hasContainerItem(stack)) {
				return stack.getItem().getContainerItem(stack);
			} else {
				return ItemStack.EMPTY;
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
		if (stack.isEmpty()) return ItemStack.EMPTY;
		stack.shrink(amount);
		return withSize(stack, stack.getCount(), container);
	}

	public static int getPlaceFromList(List<ItemStack> list, ItemStack stack, boolean wildcard) {
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				if ((stack.isEmpty() && list.get(i).isEmpty() || areItemsEqual(stack, list.get(i)))) {
					return i;
				}
			}
		}
		
		return -1;
	}
	// TODO: Make use of the tag system for this?
//	public static ItemStack fromOre(String oreDict, int stackSize) {
//		ItemStack item = ItemStack.EMPTY;
//		List<ItemStack> list = OreDictionary.getOres(oreDict);
//
//		if (!list.isEmpty()) {
//			item = list.get(0).copy();
//			item.setCount(stackSize);
//		}
//
//		return item;
//	}

	public static boolean areItemsEqual(ItemStack stack1, ItemStack stack2) {
		return !stack1.isEmpty() && !stack2.isEmpty() && stack1.isItemEqual(stack2);
	}
	
	public static boolean areStacksEqual(ItemStack stack1, ItemStack stack2) {
		return areItemsEqual(stack1, stack2) && ItemStack.areItemStackTagsEqual(stack1, stack2);
	}
	
	public static boolean canCombineStacks(ItemStack stack1, ItemStack stack2) {
		if (!stack1.isEmpty() && stack2.isEmpty()) return true;
		return areStacksEqual(stack1, stack2) && (stack1.getCount() + stack2.getCount()) <= stack1.getMaxStackSize();
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
		
		Set<String> stack1Keys = stack1.getTag().keySet();
		Set<String> stack2Keys = stack2.getTag().keySet();
		
		for (String key : stack1Keys) {
			if (stack2Keys.contains(key)) {
				if (!NBTUtil.areNBTEquals(NBTHelper.getTag(stack1, key), NBTHelper.getTag(stack2, key), true)) {
					return false;
				}
			} else {
				return false;
			}
		}
		
		return true;
	}
	
	public static EntityItem toEntity(ItemStack stack, World world, double x, double y, double z) {
		return new EntityItem(world, x, y, z, stack);
	}
}

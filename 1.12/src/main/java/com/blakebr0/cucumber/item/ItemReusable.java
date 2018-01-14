package com.blakebr0.cucumber.item;

import java.util.List;

import com.blakebr0.cucumber.lib.Tooltips;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemReusable extends ItemBase {
	
	private boolean damage;
	
	public ItemReusable(String name, int uses, boolean damage) {
		super(name);
		this.setMaxDamage(uses - 1);
		this.setMaxStackSize(1);
		this.setNoRepair();
		this.damage = damage;
	}
	
	@Override
	public boolean hasContainerItem(ItemStack stack) {
		return true;
	}
	
	@Override
	public ItemStack getContainerItem(ItemStack stack) {
		ItemStack copy = stack.copy();
		
		if (this.damage) {
			copy.setItemDamage(stack.getItemDamage() + 1);
		}
		copy.setCount(1);
		
		return copy;
	}
	
	@Override
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag advanced) {
		if (this.damage) {
			int damage = stack.getMaxDamage() - stack.getItemDamage() + 1;
			tooltip.add(Tooltips.USES_LEFT + damage);
		}
	}
}

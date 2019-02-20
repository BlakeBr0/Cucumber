package com.blakebr0.cucumber.item;

import java.util.List;

import com.blakebr0.cucumber.lib.Tooltips;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

public class ItemReusable extends ItemBase {
	
	private boolean damage;
	
	public ItemReusable(String name, int uses, boolean damage) {
		super(name, new Properties().defaultMaxDamage(uses - 1).setNoRepair());
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
			copy.setDamage(stack.getDamage() + 1);
		}
		copy.setCount(1);
		
		return copy;
	}
	
	@Override // TODO: Check out how args work with both string and itextcomponent implementations
	public void addInformation(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag advanced) {
		if (this.damage) {
			int damage = stack.getMaxDamage() - stack.getDamage() + 1;
			tooltip.add(Tooltips.USES_LEFT.get().args(damage).build());
		}
	}
}

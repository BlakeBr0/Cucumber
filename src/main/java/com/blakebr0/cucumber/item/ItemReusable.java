package com.blakebr0.cucumber.item;

import com.blakebr0.cucumber.lib.Tooltips;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import java.util.List;
import java.util.function.Function;

public class ItemReusable extends ItemBase {
	private boolean damage;

	public ItemReusable(String name, Function<Properties, Properties> properties) {
		this(name, 0, properties);
	}
	
	public ItemReusable(String name, int uses, Function<Properties, Properties> properties) {
		super(name, properties.compose(p -> p.defaultMaxDamage(Math.max(uses - 1, 0)).setNoRepair()));
		this.damage = uses > 0;
	}
	
	@Override
	public boolean hasContainerItem(ItemStack stack) {
		return true;
	}
	
	@Override
	public ItemStack getContainerItem(ItemStack stack) {
		ItemStack copy = stack.copy();
		if (this.damage)
			copy.setDamage(stack.getDamage() + 1);
		copy.setCount(1);
		
		return copy;
	}
	
	@Override
	public void addInformation(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag advanced) {
		if (this.damage) {
			int damage = stack.getMaxDamage() - stack.getDamage() + 1;
			if (damage == 1) {
				tooltip.add(Tooltips.ONE_USE_LEFT.build());
			} else {
				tooltip.add(Tooltips.USES_LEFT.args(damage).build());
			}
		} else {
			tooltip.add(Tooltips.UNLIMITED_USES.build());
		}
	}
}

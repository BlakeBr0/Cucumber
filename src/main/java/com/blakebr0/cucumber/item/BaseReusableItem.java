package com.blakebr0.cucumber.item;

import com.blakebr0.cucumber.lib.Tooltips;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.enchantment.UnbreakingEnchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;
import java.util.function.Function;

public class BaseReusableItem extends BaseItem {
	private final boolean damage;
	private final boolean tooltip;

	public BaseReusableItem(Function<Properties, Properties> properties) {
		this(true, properties);
	}

	public BaseReusableItem(boolean tooltip, Function<Properties, Properties> properties) {
		this(0, tooltip, properties);
	}

	public BaseReusableItem(int uses, Function<Properties, Properties> properties) {
		this(uses, true, properties);
	}

	public BaseReusableItem(int uses, boolean tooltip, Function<Properties, Properties> properties) {
		super(properties.compose(p -> p.defaultMaxDamage(Math.max(uses - 1, 0)).setNoRepair()));
		this.damage = uses > 0;
		this.tooltip = tooltip;
	}
	
	@Override
	public boolean hasContainerItem(ItemStack stack) {
		return true;
	}
	
	@Override
	public ItemStack getContainerItem(ItemStack stack) {
		ItemStack copy = stack.copy();
		copy.setCount(1);

		if (!this.damage)
			return copy;

		int unbreaking = EnchantmentHelper.getEnchantmentLevel(Enchantments.UNBREAKING, stack);
		for (int i = 0; i < unbreaking; i++) {
			if (UnbreakingEnchantment.negateDamage(stack, unbreaking, random))
				return copy;
		}

		copy.setDamage(stack.getDamage() + 1);
		if (copy.getDamage() > stack.getMaxDamage())
			return ItemStack.EMPTY;

		return copy;
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag advanced) {
		if (this.tooltip) {
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
}

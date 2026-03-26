package com.blakebr0.cucumber.item;

import com.blakebr0.cucumber.lib.Tooltips;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jspecify.annotations.Nullable;

import java.util.function.Consumer;
import java.util.function.Function;

public class BaseReusableItem extends BaseItem {
	private static final DeferredHolder<Enchantment, Enchantment> UNBREAKING_ENCHANTMENT = DeferredHolder.create(Enchantments.UNBREAKING);

	private final boolean unbreakable;
	private final boolean tooltip;

	public BaseReusableItem(int uses) {
		this(uses, p -> p);
	}

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
		super(properties.compose(p -> p.durability(Math.max(uses - 1, 0)).setNoCombineRepair()));
		this.unbreakable = uses < 1;
		this.tooltip = tooltip;
	}
	
	@Override
	public @Nullable ItemStackTemplate getCraftingRemainder(ItemInstance instance) {
		var template = new ItemStackTemplate(instance.typeHolder(), 1);

		if (this.unbreakable)
			return template;

		var unbreaking = template.getEnchantmentLevel(UNBREAKING_ENCHANTMENT);
		if (Math.random() > (1.0F / (unbreaking + 1)))
			return template;

		var stack = template.create();

		var newDamage = stack.getDamageValue() + 1;
		if (newDamage > stack.getMaxDamage())
			return null;

		stack.setDamageValue(newDamage);

		return ItemStackTemplate.fromNonEmptyStack(stack);
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag flag) {
		if (this.tooltip) {
			if (!this.unbreakable) {
				var damage = stack.getMaxDamage() - stack.getDamageValue() + 1;

				if (damage == 1) {
					builder.accept(Tooltips.ONE_USE_LEFT.build());
				} else {
					builder.accept(Tooltips.USES_LEFT.args(damage).build());
				}
			} else {
				builder.accept(Tooltips.UNLIMITED_USES.build());
			}
		}
	}
}

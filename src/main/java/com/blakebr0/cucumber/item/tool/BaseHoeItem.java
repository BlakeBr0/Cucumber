package com.blakebr0.cucumber.item.tool;

import com.blakebr0.cucumber.iface.IEnableable;
import net.minecraft.item.HoeItem;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import java.util.function.Function;

public class BaseHoeItem extends HoeItem {
    public BaseHoeItem(IItemTier tier, Function<Properties, Properties> properties) {
        this(tier, -2.0F, properties);
    }

    public BaseHoeItem(IItemTier tier, float attackSpeed, Function<Properties, Properties> properties) {
        super(tier, attackSpeed, properties.apply(new Properties()));
    }

    @Override
    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
        if (this instanceof IEnableable) {
            IEnableable enableable = (IEnableable) this;
            if (enableable.isEnabled())
                super.fillItemGroup(group, items);
        } else {
            super.fillItemGroup(group, items);
        }
    }
}

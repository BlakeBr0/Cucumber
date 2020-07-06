package com.blakebr0.cucumber.item.tool;

import com.blakebr0.cucumber.iface.IEnableable;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.NonNullList;

import java.util.function.Function;

public class BaseSwordItem extends SwordItem {
    private final float attackDamage;
    private final float attackSpeed;

    public BaseSwordItem(IItemTier tier, Function<Properties, Properties> properties) {
        this(tier, 3, -2.4F, properties);
    }

    public BaseSwordItem(IItemTier tier, int attackDamage, float attackSpeed, Function<Properties, Properties> properties) {
        super(tier, attackDamage, attackSpeed, properties.apply(new Properties()));
        this.attackDamage = attackDamage;
        this.attackSpeed = attackSpeed;
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

    @Override
    public float getAttackDamage() {
        return this.attackDamage;
    }

    public float getAttackSpeed() {
        return this.attackSpeed;
    }
}

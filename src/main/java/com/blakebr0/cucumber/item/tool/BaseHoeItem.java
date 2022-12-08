package com.blakebr0.cucumber.item.tool;

import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Tier;

import java.util.function.Function;

public class BaseHoeItem extends HoeItem {
    private final float attackDamage;
    private final float attackSpeed;

    public BaseHoeItem(Tier tier, Function<Properties, Properties> properties) {
        this(tier, -1, -2.0F, properties);
    }

    public BaseHoeItem(Tier tier, int attackDamage, float attackSpeed, Function<Properties, Properties> properties) {
        super(tier, attackDamage, attackSpeed, properties.apply(new Properties()));
        this.attackDamage = attackDamage;
        this.attackSpeed = attackSpeed;
    }

    public float getAttackDamage() {
        return this.attackDamage + this.getTier().getAttackDamageBonus();
    }

    public float getAttackSpeed() {
        return this.attackSpeed;
    }
}

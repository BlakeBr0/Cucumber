package com.blakebr0.cucumber.item.tool;

import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.Tier;

import java.util.function.Function;

public class BaseShovelItem extends ShovelItem {
    private final float attackDamage;
    private final float attackSpeed;

    public BaseShovelItem(Tier tier, Function<Properties, Properties> properties) {
        this(tier, 1.5F, -3.0F, properties);
    }

    public BaseShovelItem(Tier tier, float attackDamage, float attackSpeed, Function<Properties, Properties> properties) {
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

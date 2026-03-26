package com.blakebr0.cucumber.item.tool;

import com.blakebr0.cucumber.item.BaseItem;
import net.minecraft.world.item.ToolMaterial;

import java.util.function.Function;

public class BasePickaxeItem extends BaseItem {
    private final float attackDamage;
    private final float attackSpeed;

    public BasePickaxeItem(ToolMaterial material) {
        this(material, 1, -2.8F, p -> p);
    }

    public BasePickaxeItem(ToolMaterial material, Function<Properties, Properties> properties) {
        this(material, 1, -2.8F, properties);
    }

    public BasePickaxeItem(ToolMaterial material, int attackDamage, float attackSpeed, Function<Properties, Properties> properties) {
        super(properties.compose(p -> p.pickaxe(material, attackDamage, attackSpeed)));
        this.attackDamage = attackDamage + material.attackDamageBonus();
        this.attackSpeed = attackSpeed;
    }

    public float getAttackDamage() {
        return this.attackDamage;
    }

    public float getAttackSpeed() {
        return this.attackSpeed;
    }
}

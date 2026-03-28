package com.blakebr0.cucumber.item.tool;

import com.blakebr0.cucumber.item.BaseItem;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ToolMaterial;

import java.util.function.Function;

public class BaseSwordItem extends BaseItem {
    private final float attackDamage;
    private final float attackSpeed;

    public BaseSwordItem(Identifier id, ToolMaterial material) {
        this(id, material, 3, -2.4F, p -> p);
    }

    public BaseSwordItem(Identifier id, ToolMaterial material, Function<Properties, Properties> properties) {
        this(id, material, 3, -2.4F, properties);
    }

    public BaseSwordItem(Identifier id, ToolMaterial material, int attackDamage, float attackSpeed, Function<Properties, Properties> properties) {
        super(id, properties.compose(p -> p.sword(material, attackDamage, attackSpeed)));
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

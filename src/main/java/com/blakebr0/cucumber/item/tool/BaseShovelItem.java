package com.blakebr0.cucumber.item.tool;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.ToolMaterial;

import java.util.function.Function;

public class BaseShovelItem extends ShovelItem {
    private final float attackDamage;
    private final float attackSpeed;

    public BaseShovelItem(Identifier id, ToolMaterial material) {
        this(id, material, 1.5F, -3.0F, p -> p);
    }

    public BaseShovelItem(Identifier id, ToolMaterial material, Function<Properties, Properties> properties) {
        this(id, material, 1.5F, -3.0F, properties);
    }

    public BaseShovelItem(Identifier id, ToolMaterial material, float attackDamage, float attackSpeed, Function<Properties, Properties> properties) {
        super(material, attackDamage, attackSpeed, properties.apply(new Properties().setId(ResourceKey.create(Registries.ITEM, id))));
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

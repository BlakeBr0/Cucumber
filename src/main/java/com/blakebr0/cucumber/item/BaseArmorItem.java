package com.blakebr0.cucumber.item;

import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;

import java.util.function.Function;

public class BaseArmorItem extends BaseItem {
    public BaseArmorItem(ArmorMaterial material, ArmorType type) {
        super(p -> p.humanoidArmor(material, type));
    }

    public BaseArmorItem(ArmorMaterial material, ArmorType type, Function<Properties, Properties> properties) {
        super(properties.compose(p -> p.humanoidArmor(material, type)));
    }
}

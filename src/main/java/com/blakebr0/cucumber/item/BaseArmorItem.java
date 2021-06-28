package com.blakebr0.cucumber.item;

import com.blakebr0.cucumber.iface.IEnableable;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import java.util.function.Function;

public class BaseArmorItem extends ArmorItem {
    public BaseArmorItem(IArmorMaterial material, EquipmentSlotType slot, Function<Properties, Properties> properties) {
        super(material, slot, properties.apply(new Properties()));
    }

    @Override
    public void fillItemCategory(ItemGroup group, NonNullList<ItemStack> items) {
        if (this instanceof IEnableable) {
            IEnableable enableable = (IEnableable) this;
            if (enableable.isEnabled())
                super.fillItemCategory(group, items);
        } else {
            super.fillItemCategory(group, items);
        }
    }
}

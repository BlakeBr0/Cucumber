package com.blakebr0.cucumber.item.tool;

import com.blakebr0.cucumber.item.BaseItem;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ToolMaterial;

public class BaseSpearItem extends BaseItem {
    public BaseSpearItem(
            Identifier id,
            ToolMaterial material,
            float attackDuration,
            float damageMultiplier,
            float delay,
            float dismountTime,
            float dismountThreshold,
            float knockbackTime,
            float knockbackThreshold,
            float damageTime,
            float damageThreshold
    ) {
        super(id, p -> p.spear(material, attackDuration, damageMultiplier, delay, dismountTime, dismountThreshold, knockbackTime, knockbackThreshold, damageTime, damageThreshold));
    }
}

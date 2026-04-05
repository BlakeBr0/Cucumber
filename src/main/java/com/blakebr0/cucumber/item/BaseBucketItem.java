package com.blakebr0.cucumber.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluid;

import java.util.function.Function;

public class BaseBucketItem extends BucketItem {
    public BaseBucketItem(Identifier id, Fluid content) {
        this(id, content, p -> p.craftRemainder(Items.BUCKET).stacksTo(1));
    }

    public BaseBucketItem(Identifier id, Fluid content, Function<Properties, Properties> properties) {
        super(content, properties.apply(new Properties().setId(ResourceKey.create(Registries.ITEM, id))));
    }
}

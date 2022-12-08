package com.blakebr0.cucumber.item.tool;

import net.minecraft.client.renderer.item.ItemPropertyFunction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.FishingRodItem;

import java.util.function.Function;

public class BaseFishingRodItem extends FishingRodItem {
    public BaseFishingRodItem(Function<Properties, Properties> properties) {
        super(properties.apply(new Properties()));
    }

    public static ItemPropertyFunction getCastPropertyGetter() {
        return (stack, level, entity, _unused) -> {
            if (entity == null)
                return 0.0F;

            boolean flag = entity.getMainHandItem() == stack;
            boolean flag1 = entity.getOffhandItem() == stack;
            if (entity.getMainHandItem().getItem() instanceof FishingRodItem) {
                flag1 = false;
            }

            return (flag || flag1) && entity instanceof Player player && player.fishing != null ? 1.0F : 0.0F;
        };
    }
}

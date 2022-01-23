package com.blakebr0.cucumber.item.tool;

import com.blakebr0.cucumber.iface.ICustomBow;
import com.blakebr0.cucumber.iface.IEnableable;
import net.minecraft.client.renderer.item.ItemPropertyFunction;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import java.util.function.Function;

public class BaseBowItem extends BowItem implements ICustomBow {
    public BaseBowItem(Function<Properties, Properties> properties) {
        super(properties.apply(new Properties()));
    }

    @Override
    public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) {
        if (this instanceof IEnableable enableable) {
            if (enableable.isEnabled())
                super.fillItemCategory(group, items);
        } else {
            super.fillItemCategory(group, items);
        }
    }

    @Override
    public boolean hasFOVChange() {
        return true;
    }

    public static ItemPropertyFunction getPullPropertyGetter() {
        return (stack, level, entity, _unused) -> {
            if (entity == null) {
                return 0.0F;
            } else {
                return entity.getUseItem() != stack ? 0.0F : (float) (stack.getUseDuration() - entity.getUseItemRemainingTicks()) * ((ICustomBow) stack.getItem()).getDrawSpeedMulti(stack) / 20.0F;
            }
        };
    }

    public static ItemPropertyFunction getPullingPropertyGetter() {
        return (stack, level, entity, _unused) -> {
            return entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F;
        };
    }
}

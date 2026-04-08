package com.blakebr0.cucumber.client.properties;

import com.blakebr0.cucumber.iface.ICustomBow;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.numeric.RangeSelectItemModelProperty;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;

public record CustomBowPullProperty() implements RangeSelectItemModelProperty {
    public static final MapCodec<CustomBowPullProperty> MAP_CODEC = MapCodec.unit(new CustomBowPullProperty());

    @Override
    public float get(ItemStack stack, @Nullable ClientLevel level, @Nullable ItemOwner owner, int seed) {
        if (owner != null) {
            var entity = owner.asLivingEntity();
            if (entity != null && entity.getUseItem() == stack) {
                return (float) (stack.getUseDuration(entity) - entity.getUseItemRemainingTicks()) * ((ICustomBow) stack.getItem()).getDrawSpeedMulti(stack) / 20.0F;
            }
        }

        return 0.0F;
    }

    @Override
    public MapCodec<? extends RangeSelectItemModelProperty> type() {
        return MAP_CODEC;
    }
}

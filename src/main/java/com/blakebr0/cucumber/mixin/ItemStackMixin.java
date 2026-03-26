package com.blakebr0.cucumber.mixin;

import com.blakebr0.cucumber.event.ItemBreakEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.NeoForge;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
    @Shadow public abstract ItemStack copy();

    @Inject(
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;shrink(I)V", ordinal = 0),
            method = "applyDamage(ILnet/minecraft/world/entity/LivingEntity;Ljava/util/function/Consumer;)V"
    )
    public void cucumber$applyDamage(int newDamage, @Nullable LivingEntity player, Consumer<Item> onBreak, CallbackInfo ci) {
        var stack = this.copy();
        NeoForge.EVENT_BUS.post(new ItemBreakEvent(stack, newDamage, player));
    }
}

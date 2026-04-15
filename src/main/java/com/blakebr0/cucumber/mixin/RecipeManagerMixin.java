package com.blakebr0.cucumber.mixin;

import com.blakebr0.cucumber.helper.RecipeHelper;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeMap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(RecipeManager.class)
public class RecipeManagerMixin {
    @Inject(
            at = @At(value = "INVOKE", shift = At.Shift.AFTER, target = "Ljava/util/SortedMap;forEach(Ljava/util/function/BiConsumer;)V"),
            method = "prepare(Lnet/minecraft/server/packs/resources/ResourceManager;Lnet/minecraft/util/profiling/ProfilerFiller;)Lnet/minecraft/world/item/crafting/RecipeMap;"
    )
    public void cucumber$prepare(
            ResourceManager manager,
            ProfilerFiller profiler,
            CallbackInfoReturnable<RecipeMap> cir,
            @Local(name = "recipeHolders") List<RecipeHolder<?>> recipeHolders
    ) {
        RecipeHelper.fireRecipeManagerLoadingEvent((RecipeManager) (Object) this, recipeHolders);
    }
}

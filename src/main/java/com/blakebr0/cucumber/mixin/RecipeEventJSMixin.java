package com.blakebr0.cucumber.mixin;

import com.blakebr0.cucumber.helper.RecipeHelper;
import com.google.gson.JsonElement;
import dev.latvian.mods.kubejs.recipe.RecipesEventJS;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(value = RecipesEventJS.class, remap = false)
public class RecipeEventJSMixin {
    @Inject(method = "post", at = @At("TAIL"))
    public void cucumber$post(RecipeManager manager, Map<ResourceLocation, JsonElement> recipes, CallbackInfo ci) {
        RecipeHelper.fireRecipeManagerLoadedEventKubeJSEdition(manager);
    }
}

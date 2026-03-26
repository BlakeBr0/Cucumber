package com.blakebr0.cucumber.mixin;

import com.blakebr0.cucumber.helper.RecipeHelper;
import net.minecraft.server.ReloadableServerResources;
import net.minecraft.world.item.crafting.RecipeManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ReloadableServerResources.class)
public class ReloadableServerResourcesMixin {
    @Shadow @Final private RecipeManager recipes;

    /**
     * At this point recipes should be loaded in a finalized state and should be usable for accessing recipes.
     */
    @Inject(at = @At("RETURN"), method = "updateComponentsAndStaticRegistryTags()V")
    public void cucumber$updateRegistryTags(CallbackInfo ci) {
        RecipeHelper.fireRecipeManagerLoadedEvent(this.recipes);
    }
}

package com.blakebr0.cucumber.mixin;

import com.blakebr0.cucumber.helper.RecipeHelper;
import net.minecraft.commands.Commands;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.LayeredRegistryAccess;
import net.minecraft.server.ReloadableServerResources;
import net.minecraft.server.permissions.PermissionSet;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.crafting.RecipeManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(ReloadableServerResources.class)
public class ReloadableServerResourcesMixin {
    @Shadow @Final private RecipeManager recipes;

    @Inject(at = @At(value = "RETURN"), method = "<init>")
    public void cucumber$constructor(
            LayeredRegistryAccess fullLayers,
            HolderLookup.Provider loadingContext,
            FeatureFlagSet enabledFeatures,
            Commands.CommandSelection commandSelection,
            List postponedTags,
            PermissionSet functionCompilationPermissions,
            List newComponents,
            CallbackInfo ci
    ) {
        RecipeHelper.setRecipeManager(this.recipes);
    }

    /**
     * At this point recipes should be loaded in a finalized state and should be usable for accessing recipes.
     */
    @Inject(at = @At("RETURN"), method = "updateComponentsAndStaticRegistryTags()V")
    public void cucumber$updateRegistryTags(CallbackInfo ci) {
        RecipeHelper.fireRecipeManagerLoadedEvent(this.recipes);
    }
}

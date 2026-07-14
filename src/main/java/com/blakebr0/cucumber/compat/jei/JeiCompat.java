package com.blakebr0.cucumber.compat.jei;

import com.blakebr0.cucumber.Cucumber;
import com.blakebr0.cucumber.crafting.recipe.ShapedTagRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IVanillaCategoryExtensionRegistration;
import net.minecraft.resources.Identifier;

@JeiPlugin
public final class JeiCompat implements IModPlugin {
    private static final Identifier UID = Cucumber.resource("jei_plugin");

    @Override
    public Identifier getPluginUid() {
        return UID;
    }

    @Override
    public void registerVanillaCategoryExtensions(IVanillaCategoryExtensionRegistration registration) {
        registration.getCraftingCategory().addExtension(ShapedTagRecipe.class, new ShapedTagRecipeCategoryExtension());
    }
}

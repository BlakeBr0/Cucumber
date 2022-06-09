package com.blakebr0.cucumber.init;

import com.blakebr0.cucumber.Cucumber;
import com.blakebr0.cucumber.crafting.conditions.EnableableCondition;
import com.blakebr0.cucumber.crafting.recipe.ShapedNoMirrorRecipe;
import com.blakebr0.cucumber.crafting.recipe.ShapedTagRecipe;
import com.blakebr0.cucumber.crafting.recipe.ShapedTransferDamageRecipe;
import com.blakebr0.cucumber.crafting.recipe.ShapelessTagRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;

public final class ModRecipeSerializers {
    public static final RecipeSerializer<?> CRAFTING_SHAPED_NO_MIRROR = new ShapedNoMirrorRecipe.Serializer();
    public static final RecipeSerializer<?> CRAFTING_SHAPED_TRANSFER_DAMAGE = new ShapedTransferDamageRecipe.Serializer();
    public static final RecipeSerializer<?> CRAFTING_SHAPED_TAG = new ShapedTagRecipe.Serializer();
    public static final RecipeSerializer<?> CRAFTING_SHAPELESS_TAG = new ShapelessTagRecipe.Serializer();

    @SubscribeEvent
    public void onRegisterSerializers(RegisterEvent event) {
        event.register(ForgeRegistries.Keys.RECIPE_SERIALIZERS, registry -> {
            registry.register(new ResourceLocation(Cucumber.MOD_ID, "shaped_no_mirror"), CRAFTING_SHAPED_NO_MIRROR);
            registry.register(new ResourceLocation(Cucumber.MOD_ID, "shaped_transfer_damage"), CRAFTING_SHAPED_TRANSFER_DAMAGE);
            registry.register(new ResourceLocation(Cucumber.MOD_ID, "shaped_tag"), CRAFTING_SHAPED_TAG);
            registry.register(new ResourceLocation(Cucumber.MOD_ID, "shapeless_tag"), CRAFTING_SHAPELESS_TAG);

            CraftingHelper.register(EnableableCondition.Serializer.INSTANCE);
        });
    }
}

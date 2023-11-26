package com.blakebr0.cucumber.init;

import com.blakebr0.cucumber.Cucumber;
import com.blakebr0.cucumber.crafting.conditions.FeatureFlagCondition;
import com.blakebr0.cucumber.crafting.recipe.ShapedNoMirrorRecipe;
import com.blakebr0.cucumber.crafting.recipe.ShapedTagRecipe;
import com.blakebr0.cucumber.crafting.recipe.ShapedTransferDamageRecipe;
import com.blakebr0.cucumber.crafting.recipe.ShapedTransferNBTRecipe;
import com.blakebr0.cucumber.crafting.recipe.ShapelessTagRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public final class ModRecipeSerializers {
    public static final DeferredRegister<RecipeSerializer<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Cucumber.MOD_ID);

    public static final RegistryObject<RecipeSerializer<?>> CRAFTING_SHAPED_NO_MIRROR = register("shaped_no_mirror", ShapedNoMirrorRecipe.Serializer::new);
    public static final RegistryObject<RecipeSerializer<?>> CRAFTING_SHAPED_TRANSFER_DAMAGE = register("shaped_transfer_damage", ShapedTransferDamageRecipe.Serializer::new);
    public static final RegistryObject<RecipeSerializer<?>> CRAFTING_SHAPED_TRANSFER_NBT = register("shaped_transfer_nbt", ShapedTransferNBTRecipe.Serializer::new);
    public static final RegistryObject<RecipeSerializer<?>> CRAFTING_SHAPED_TAG = register("shaped_tag", ShapedTagRecipe.Serializer::new);
    public static final RegistryObject<RecipeSerializer<?>> CRAFTING_SHAPELESS_TAG = register("shapeless_tag", ShapelessTagRecipe.Serializer::new);

    @SubscribeEvent
    public void onRegisterSerializers(RegisterEvent event) {
        event.register(ForgeRegistries.Keys.RECIPE_SERIALIZERS, registry -> {
            CraftingHelper.register(FeatureFlagCondition.Serializer.INSTANCE);
        });
    }

    private static RegistryObject<RecipeSerializer<?>> register(String name, Supplier<RecipeSerializer<?>> serializer) {
        return REGISTRY.register(name, serializer);
    }
}

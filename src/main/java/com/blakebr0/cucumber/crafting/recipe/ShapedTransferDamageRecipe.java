package com.blakebr0.cucumber.crafting.recipe;

import com.blakebr0.cucumber.init.ModRecipeSerializers;
import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;

public class ShapedTransferDamageRecipe extends ShapedRecipe {
    private final ItemStack result;

    public ShapedTransferDamageRecipe(ResourceLocation id, String group, CraftingBookCategory category, int width, int height, NonNullList<Ingredient> inputs, ItemStack result, boolean showNotification) {
        super(id, group, category, width, height, inputs, result, showNotification);
        this.result = result;
    }

    @Override
    public ItemStack assemble(CraftingContainer inv, RegistryAccess access) {
        var damageable = ItemStack.EMPTY;

        for (var i = 0; i < inv.getContainerSize(); i++) {
            var slotStack = inv.getItem(i);

            if (slotStack.isDamageableItem()) {
                damageable = slotStack;
                break;
            }
        }

        if (damageable.isEmpty())
            return super.assemble(inv, access);

        var result = this.getResultItem(access).copy();

        result.setDamageValue(damageable.getDamageValue());

        return result;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.CRAFTING_SHAPED_TRANSFER_DAMAGE.get();
    }

    public static class Serializer implements RecipeSerializer<ShapedTransferDamageRecipe> {
        @Override
        public ShapedTransferDamageRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            var group = GsonHelper.getAsString(json, "group", "");
            var category = CraftingBookCategory.CODEC.byName(GsonHelper.getAsString(json, "category", null), CraftingBookCategory.MISC);
            var key = ShapedRecipe.keyFromJson(GsonHelper.getAsJsonObject(json, "key"));
            var pattern = ShapedRecipe.shrink(ShapedRecipe.patternFromJson(GsonHelper.getAsJsonArray(json, "pattern")));
            var width = pattern[0].length();
            var height = pattern.length;
            var ingredients = ShapedRecipe.dissolvePattern(pattern, key, width, height);
            var result = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "result"));
            var showNotification = GsonHelper.getAsBoolean(json, "show_notification", true);

            return new ShapedTransferDamageRecipe(recipeId, group, category, width, height, ingredients, result, showNotification);
        }

        @Override
        public ShapedTransferDamageRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            var group = buffer.readUtf(32767);
            var category = buffer.readEnum(CraftingBookCategory.class);
            var width = buffer.readVarInt();
            var height = buffer.readVarInt();
            var ingredients = NonNullList.withSize(width * height, Ingredient.EMPTY);

            for (var k = 0; k < ingredients.size(); ++k) {
                ingredients.set(k, Ingredient.fromNetwork(buffer));
            }

            var result = buffer.readItem();
            var showNotification = buffer.readBoolean();

            return new ShapedTransferDamageRecipe(recipeId, group, category, width, height, ingredients, result, showNotification);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, ShapedTransferDamageRecipe recipe) {
            buffer.writeUtf(recipe.getGroup());
            buffer.writeEnum(recipe.category());
            buffer.writeVarInt(recipe.getRecipeWidth());
            buffer.writeVarInt(recipe.getRecipeHeight());

            for (var ingredient : recipe.getIngredients()) {
                ingredient.toNetwork(buffer);
            }

            buffer.writeItem(recipe.result);
            buffer.writeBoolean(recipe.showNotification());
        }
    }
}

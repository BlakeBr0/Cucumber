package com.blakebr0.cucumber.crafting.recipe;

import com.blakebr0.cucumber.init.ModRecipeSerializers;
import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;

public class ShapedTransferDamageRecipe extends ShapedRecipe {
    private final boolean transferNBT;

    public ShapedTransferDamageRecipe(ResourceLocation id, String group, int width, int height, NonNullList<Ingredient> inputs, ItemStack output, boolean transferNBT) {
        super(id, group, width, height, inputs, output);
        this.transferNBT = transferNBT;
    }

    @Override
    public ItemStack assemble(CraftingContainer inv) {
        var damageable = ItemStack.EMPTY;

        for (var i = 0; i < inv.getContainerSize(); i++) {
            var slotStack = inv.getItem(i);

            if (slotStack.isDamageableItem()) {
                damageable = slotStack;
                break;
            }
        }

        if (damageable.isEmpty())
            return super.assemble(inv);

        var result = this.getResultItem().copy();

        if (this.transferNBT) {
            var tag = damageable.getTag();

            if (tag != null) {
                result.setTag(tag.copy());
            }
        } else {
            result.setDamageValue(damageable.getDamageValue());
        }

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
            var key = ShapedRecipe.keyFromJson(GsonHelper.getAsJsonObject(json, "key"));
            var pattern = ShapedRecipe.shrink(ShapedRecipe.patternFromJson(GsonHelper.getAsJsonArray(json, "pattern")));
            var width = pattern[0].length();
            var height = pattern.length;
            var ingredients = ShapedRecipe.dissolvePattern(pattern, key, width, height);
            var output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "result"));
            var transferNBT = GsonHelper.getAsBoolean(json, "transfer_nbt", false);

            return new ShapedTransferDamageRecipe(recipeId, group, width, height, ingredients, output, transferNBT);
        }

        @Override
        public ShapedTransferDamageRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            var group = buffer.readUtf(32767);
            var width = buffer.readVarInt();
            var height = buffer.readVarInt();
            var ingredients = NonNullList.withSize(width * height, Ingredient.EMPTY);

            for (var k = 0; k < ingredients.size(); ++k) {
                ingredients.set(k, Ingredient.fromNetwork(buffer));
            }

            var output = buffer.readItem();
            var transferNBT = buffer.readBoolean();

            return new ShapedTransferDamageRecipe(recipeId, group, width, height, ingredients, output, transferNBT);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, ShapedTransferDamageRecipe recipe) {
            buffer.writeUtf(recipe.getGroup());
            buffer.writeVarInt(recipe.getRecipeWidth());
            buffer.writeVarInt(recipe.getRecipeHeight());

            for (var ingredient : recipe.getIngredients()) {
                ingredient.toNetwork(buffer);
            }

            buffer.writeItem(recipe.getResultItem());
            buffer.writeBoolean(recipe.transferNBT);
        }
    }
}

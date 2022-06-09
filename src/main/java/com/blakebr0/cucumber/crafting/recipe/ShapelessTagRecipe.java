package com.blakebr0.cucumber.crafting.recipe;

import com.blakebr0.cucumber.crafting.TagMapper;
import com.blakebr0.cucumber.init.ModRecipeSerializers;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapelessRecipe;

public class ShapelessTagRecipe extends ShapelessRecipe {
    private final String tag;
    private final int count;
    private ItemStack output;

    public ShapelessTagRecipe(ResourceLocation id, String group, NonNullList<Ingredient> inputs, String tag, int count) {
        super(id, group, ItemStack.EMPTY, inputs);
        this.tag = tag;
        this.count = count;
    }

    @Override
    public ItemStack getResultItem() {
        if (this.output == null) {
            this.output = TagMapper.getItemStackForTag(this.tag, this.count);
        }

        return this.output;
    }

    @Override
    public boolean isSpecial() {
        if (this.output == null) {
            this.output = TagMapper.getItemStackForTag(this.tag, this.count);
        }

        return this.output.isEmpty();
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.CRAFTING_SHAPED_TAG;
    }

    public static class Serializer implements RecipeSerializer<ShapelessTagRecipe> {
        @Override
        public ShapelessTagRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            var group = GsonHelper.getAsString(json, "group", "");
            var ingredients = readIngredients(GsonHelper.getAsJsonArray(json, "ingredients"));

            if (ingredients.isEmpty()) {
                throw new JsonParseException("No ingredients for shapeless recipe");
            } else if (ingredients.size() > 9) {
                throw new JsonParseException("Too many ingredients for shapeless recipe the max is 9");
            }

            var result = GsonHelper.getAsJsonObject(json, "result");
            var tag = GsonHelper.getAsString(result, "tag");
            var count = GsonHelper.getAsInt(result, "count", 1);

            return new ShapelessTagRecipe(recipeId, group, ingredients, tag, count);
        }

        @Override
        public ShapelessTagRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            var group = buffer.readUtf(32767);
            var size = buffer.readVarInt();
            var ingredients = NonNullList.withSize(size, Ingredient.EMPTY);

            for (var j = 0; j < ingredients.size(); j++) {
                ingredients.set(j, Ingredient.fromNetwork(buffer));
            }

            var tag = buffer.readUtf();
            var count = buffer.readVarInt();

            return new ShapelessTagRecipe(recipeId, group, ingredients, tag, count);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, ShapelessTagRecipe recipe) {
            buffer.writeUtf(recipe.getGroup());
            buffer.writeVarInt(recipe.getIngredients().size());

            for (var ingredient : recipe.getIngredients()) {
                ingredient.toNetwork(buffer);
            }

            buffer.writeUtf(recipe.tag);
            buffer.writeVarInt(recipe.count);
        }

        private static NonNullList<Ingredient> readIngredients(JsonArray ingredientArray) {
            NonNullList<Ingredient> ingredients = NonNullList.create();

            for (int i = 0; i < ingredientArray.size(); ++i) {
                var ingredient = Ingredient.fromJson(ingredientArray.get(i));

                if (!ingredient.isEmpty()) {
                    ingredients.add(ingredient);
                }
            }

            return ingredients;
        }
    }
}

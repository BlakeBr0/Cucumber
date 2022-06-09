package com.blakebr0.cucumber.crafting.recipe;

import com.blakebr0.cucumber.crafting.TagMapper;
import com.blakebr0.cucumber.init.ModRecipeSerializers;
import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;

public class ShapedTagRecipe extends ShapedNoMirrorRecipe {
    private final String tag;
    private final int count;
    private ItemStack output;

    public ShapedTagRecipe(ResourceLocation id, String group, int width, int height, NonNullList<Ingredient> inputs, String tag, int count) {
        super(id, group, width, height, inputs, ItemStack.EMPTY);
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

    public static class Serializer implements RecipeSerializer<ShapedTagRecipe> {
        @Override
        public ShapedTagRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            var group = GsonHelper.getAsString(json, "group", "");
            var key = ShapedRecipe.keyFromJson(GsonHelper.getAsJsonObject(json, "key"));
            var pattern = ShapedRecipe.shrink(ShapedRecipe.patternFromJson(GsonHelper.getAsJsonArray(json, "pattern")));
            var width = pattern[0].length();
            var height = pattern.length;
            var ingredients = ShapedRecipe.dissolvePattern(pattern, key, width, height);
            var result = GsonHelper.getAsJsonObject(json, "result");
            var tag = GsonHelper.getAsString(result, "tag");
            var count = GsonHelper.getAsInt(result, "count", 1);

            return new ShapedTagRecipe(recipeId, group, width, height, ingredients, tag, count);
        }

        @Override
        public ShapedTagRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            var group = buffer.readUtf(32767);
            var width = buffer.readVarInt();
            var height = buffer.readVarInt();
            var ingredients = NonNullList.withSize(width * height, Ingredient.EMPTY);

            for (var k = 0; k < ingredients.size(); k++) {
                ingredients.set(k, Ingredient.fromNetwork(buffer));
            }

            var tag = buffer.readUtf();
            var count = buffer.readVarInt();

            return new ShapedTagRecipe(recipeId, group, width, height, ingredients, tag, count);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, ShapedTagRecipe recipe) {
            buffer.writeUtf(recipe.getGroup());
            buffer.writeVarInt(recipe.getWidth());
            buffer.writeVarInt(recipe.getHeight());

            for (var ingredient : recipe.getIngredients()) {
                ingredient.toNetwork(buffer);
            }

            buffer.writeUtf(recipe.tag);
            buffer.writeVarInt(recipe.count);
        }
    }
}

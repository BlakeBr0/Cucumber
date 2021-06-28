package com.blakebr0.cucumber.crafting.recipe;

import com.blakebr0.cucumber.crafting.TagMapper;
import com.blakebr0.cucumber.init.ModRecipeSerializers;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapelessRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class ShapelessTagRecipe extends ShapelessRecipe {
    public ShapelessTagRecipe(ResourceLocation id, String group, ItemStack output, NonNullList<Ingredient> inputs) {
        super(id, group, output, inputs);
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.CRAFTING_SHAPED_TAG;
    }

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<ShapelessTagRecipe> {
        @Override
        public ShapelessTagRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            String group = JSONUtils.getAsString(json, "group", "");
            NonNullList<Ingredient> inputs = readIngredients(JSONUtils.getAsJsonArray(json, "ingredients"));

            if (inputs.isEmpty()) {
                throw new JsonParseException("No ingredients for shapeless recipe");
            } else if (inputs.size() > 9) {
                throw new JsonParseException("Too many ingredients for shapeless recipe the max is 9");
            }

            JsonObject result = JSONUtils.getAsJsonObject(json, "result");
            String tag = JSONUtils.getAsString(result, "tag");
            int count = JSONUtils.getAsInt(result, "count", 1);
            Item item = TagMapper.getItemForTag(tag);
            if (item == Items.AIR)
                return null;

            ItemStack output = new ItemStack(item, count);

            return new ShapelessTagRecipe(recipeId, group, output, inputs);
        }

        @Override
        public ShapelessTagRecipe fromNetwork(ResourceLocation recipeId, PacketBuffer buffer) {
            String group = buffer.readUtf(32767);
            int size = buffer.readVarInt();
            NonNullList<Ingredient> inputs = NonNullList.withSize(size, Ingredient.EMPTY);

            for (int j = 0; j < inputs.size(); j++) {
                inputs.set(j, Ingredient.fromNetwork(buffer));
            }

            ItemStack output = buffer.readItem();

            return new ShapelessTagRecipe(recipeId, group, output, inputs);
        }

        @Override
        public void toNetwork(PacketBuffer buffer, ShapelessTagRecipe recipe) {
            buffer.writeUtf(recipe.getGroup());
            buffer.writeVarInt(recipe.getIngredients().size());

            for (Ingredient ingredient : recipe.getIngredients()) {
                ingredient.toNetwork(buffer);
            }

            buffer.writeItem(recipe.getResultItem());
        }

        private static NonNullList<Ingredient> readIngredients(JsonArray ingredientArray) {
            NonNullList<Ingredient> ingredients = NonNullList.create();

            for (int i = 0; i < ingredientArray.size(); ++i) {
                Ingredient ingredient = Ingredient.fromJson(ingredientArray.get(i));
                if (!ingredient.isEmpty()) {
                    ingredients.add(ingredient);
                }
            }

            return ingredients;
        }
    }
}

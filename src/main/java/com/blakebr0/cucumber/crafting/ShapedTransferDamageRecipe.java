package com.blakebr0.cucumber.crafting;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;

import java.util.Map;
import java.util.Set;

public class ShapedTransferDamageRecipe extends ShapedRecipe {
    public ShapedTransferDamageRecipe(ResourceLocation id, String group, int width, int height, NonNullList<Ingredient> inputs, ItemStack output) {
        super(id, group, width, height, inputs, output);
    }

    @Override
    public ItemStack getCraftingResult(CraftingInventory inv) {
        ItemStack damageable = ItemStack.EMPTY;
        for (int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack slotStack = inv.getStackInSlot(i);
            if (slotStack.isDamageable()) {
                damageable = slotStack;
                break;
            }
        }

        if (damageable.isEmpty())
            return super.getCraftingResult(inv);

        ItemStack result = this.getRecipeOutput().copy();
        result.setDamage(damageable.getDamage());

        return result;
    }

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<ShapedTransferDamageRecipe> {
        public ShapedTransferDamageRecipe read(ResourceLocation recipeId, JsonObject json) {
            String s = JSONUtils.getString(json, "group", "");
            Map<String, Ingredient> map = ShapedRecipe.deserializeKey(JSONUtils.getJsonObject(json, "key"));
            String[] astring = ShapedRecipe.shrink(ShapedRecipe.patternFromJson(JSONUtils.getJsonArray(json, "pattern")));
            int i = astring[0].length();
            int j = astring.length;
            NonNullList<Ingredient> nonnulllist = ShapedRecipe.deserializeIngredients(astring, map, i, j);
            ItemStack itemstack = ShapedRecipe.deserializeItem(JSONUtils.getJsonObject(json, "result"));
            return new ShapedTransferDamageRecipe(recipeId, s, i, j, nonnulllist, itemstack);
        }

        public ShapedTransferDamageRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
            int i = buffer.readVarInt();
            int j = buffer.readVarInt();
            String s = buffer.readString(32767);
            NonNullList<Ingredient> inputs = NonNullList.withSize(i * j, Ingredient.EMPTY);

            for (int k = 0; k < inputs.size(); ++k) {
                inputs.set(k, Ingredient.read(buffer));
            }

            ItemStack output = buffer.readItemStack();
            return new ShapedTransferDamageRecipe(recipeId, s, i, j, inputs, output);
        }

        public void write(PacketBuffer buffer, ShapedTransferDamageRecipe recipe) {
            buffer.writeVarInt(recipe.getRecipeWidth());
            buffer.writeVarInt(recipe.getRecipeHeight());
            buffer.writeString(recipe.getGroup());

            for (Ingredient ingredient : recipe.getIngredients()) {
                ingredient.write(buffer);
            }

            buffer.writeItemStack(recipe.getRecipeOutput());
        }
    }
}

package com.blakebr0.cucumber.crafting;

import com.google.gson.JsonObject;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;

@Deprecated // TODO: REMOVE
public interface ISpecialRecipeSerializer<T extends ISpecialRecipe> {
    T read(ResourceLocation recipeId, JsonObject json);
    T read(ResourceLocation recipeId, PacketBuffer buffer);
    void write(PacketBuffer buffer, T recipe);
}

package com.blakebr0.cucumber.helper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.TagParser;
import net.minecraft.util.GsonHelper;

public final class ParsingHelper {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    public static int parseHex(String s, String name) {
        // remove leading # if it exists
        if (s.startsWith("#")) {
            s = s.substring(1);
        }

        try {
            return Integer.parseInt(s, 16);
        } catch (NumberFormatException e) {
            throw new JsonSyntaxException("Invalid color provided for color " + name);
        }
    }

    public static CompoundTag parseNBT(JsonElement json) {
        try {
            if (json.isJsonObject()) {
                return TagParser.parseTag(GSON.toJson(json));
            } else {
                return TagParser.parseTag(GsonHelper.convertToString(json, "nbt"));
            }
        } catch (CommandSyntaxException e) {
            throw new JsonSyntaxException("Invalid NBT entry: " + e.toString());
        }
    }
}

package com.blakebr0.cucumber.crafting;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapedRecipePattern;

import java.util.List;
import java.util.function.Function;

public final class ShapedRecipePatternCodecs {
    // this is a copy of ShapedRecipePattern.Data#PATTERN_CODEC that removes the maxWith and maxHeight checks
    private static final Codec<List<String>> PATTERN_CODEC = Codec.STRING.listOf().comapFlatMap((pattern) -> {
        if (pattern.isEmpty()) {
            return DataResult.error(() -> "Invalid pattern: empty pattern not allowed");
        } else {
            int length = pattern.getFirst().length();
            var lines = pattern.iterator();

            String line;
            do {
                if (!lines.hasNext()) {
                    return DataResult.success(pattern);
                }

                line = lines.next();
            } while (length == line.length());

            return DataResult.error(() -> "Invalid pattern: each row must be the same width");
        }
    }, Function.identity());
    // copy of ShapedRecipePattern.Data#SYMBOL_CODEC
    public static final Codec<Character> SYMBOL_CODEC = Codec.STRING.comapFlatMap((symbol) -> {
        if (symbol.length() != 1) {
            return DataResult.error(() -> "Invalid key entry: '" + symbol + "' is an invalid symbol (must be 1 character only).");
        } else {
            return " ".equals(symbol) ? DataResult.error(() -> "Invalid key entry: ' ' is a reserved symbol.") : DataResult.success(symbol.charAt(0));
        }
    }, String::valueOf);
    // copy of ShapedRecipePattern.Data#MAP_CODEC that uses the modified PATTERN_CODEC
    private static final MapCodec<ShapedRecipePattern.Data> DATA_MAP_CODEC = RecordCodecBuilder.mapCodec((builder) ->
            builder.group(
                    ExtraCodecs.strictUnboundedMap(SYMBOL_CODEC, Ingredient.CODEC).fieldOf("key").forGetter(ShapedRecipePattern.Data::key),
                    PATTERN_CODEC.fieldOf("pattern").forGetter(ShapedRecipePattern.Data::pattern)
            ).apply(builder, ShapedRecipePattern.Data::new)
    );

    // copy of ShapedRecipePattern.MAP_CODEC which makes use of the custom ShapedRecipePattern.Data#MAP_CODEC
    public static final MapCodec<ShapedRecipePattern> MAP_CODEC = DATA_MAP_CODEC.flatXmap(ShapedRecipePattern::unpack, (pattern) ->
            pattern.data.map(DataResult::success).orElseGet(() -> DataResult.error(() -> "Cannot encode unpacked recipe"))
    );
}

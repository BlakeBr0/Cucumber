package com.blakebr0.cucumber.crafting.recipe;

import com.blakebr0.cucumber.crafting.OutputResolver;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.NormalCraftingRecipe;
import net.minecraft.world.item.crafting.PlacementInfo;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipePattern;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.ShapedCraftingRecipeDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.Nullable;

import java.util.List;

public class ShapedTagRecipe extends NormalCraftingRecipe {
    public static final MapCodec<ShapedTagRecipe> MAP_CODEC = RecordCodecBuilder.mapCodec(builder ->
            builder.group(
                    Recipe.CommonInfo.MAP_CODEC.forGetter(o -> o.commonInfo),
                    CraftingRecipe.CraftingBookInfo.MAP_CODEC.forGetter(o -> o.bookInfo),
                    ShapedRecipePattern.MAP_CODEC.forGetter(recipe -> recipe.pattern),
                    OutputResolver.Tag.CODEC.fieldOf("result").forGetter(recipe -> (OutputResolver.Tag) recipe.outputResolver)
            ).apply(builder, ShapedTagRecipe::new)
    );
    public static final StreamCodec<RegistryFriendlyByteBuf, ShapedTagRecipe> STREAM_CODEC = StreamCodec.of(
            ShapedTagRecipe::toNetwork, ShapedTagRecipe::fromNetwork
    );
    public static final RecipeSerializer<ShapedTagRecipe> SERIALIZER = new RecipeSerializer<>(MAP_CODEC, STREAM_CODEC);

    private final OutputResolver outputResolver;
    private final ShapedRecipePattern pattern;
    private @Nullable ItemStack result;

    public ShapedTagRecipe(CommonInfo commonInfo, CraftingBookInfo craftingBookInfo, ShapedRecipePattern pattern, OutputResolver outputResolver) {
        super(commonInfo, craftingBookInfo);
        this.outputResolver = outputResolver;
        this.pattern = pattern;
    }

    @Override
    public ItemStack assemble(CraftingInput input) {
        if (this.result == null) {
            this.result = this.outputResolver.resolve();
        }

        return this.result;
    }

    @Override
    public boolean matches(CraftingInput input, Level level) {
        return this.pattern.matches(input);
    }

    @Override
    public boolean isSpecial() {
        if (this.result == null) {
            this.result = this.outputResolver.resolve();
        }

        return this.result.isEmpty();
    }

    @Override
    public RecipeSerializer<ShapedTagRecipe> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public List<RecipeDisplay> display() {
        var result = ItemStackTemplate.fromNonEmptyStack(this.outputResolver.resolve());

        return List.of(
                new ShapedCraftingRecipeDisplay(
                        this.pattern.width(),
                        this.pattern.height(),
                        this.pattern.ingredients().stream().map(e -> e.map(Ingredient::display).orElse(SlotDisplay.Empty.INSTANCE)).toList(),
                        new SlotDisplay.ItemStackSlotDisplay(result),
                        new SlotDisplay.ItemSlotDisplay(Items.CRAFTING_TABLE)
                )
        );
    }

    @Override
    public PlacementInfo createPlacementInfo() {
        return PlacementInfo.createFromOptionals(this.pattern.ingredients());
    }

    private static ShapedTagRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
        var commonInfo = CommonInfo.STREAM_CODEC.decode(buffer);
        var craftingBookInfo = CraftingBookInfo.STREAM_CODEC.decode(buffer);
        var pattern = ShapedRecipePattern.STREAM_CODEC.decode(buffer);
        var result = OutputResolver.create(buffer);

        return new ShapedTagRecipe(commonInfo, craftingBookInfo, pattern, result);
    }

    private static void toNetwork(RegistryFriendlyByteBuf buffer, ShapedTagRecipe recipe) {
        CommonInfo.STREAM_CODEC.encode(buffer, recipe.commonInfo);
        CraftingBookInfo.STREAM_CODEC.encode(buffer, recipe.bookInfo);
        ShapedRecipePattern.STREAM_CODEC.encode(buffer, recipe.pattern);
        ItemStack.OPTIONAL_STREAM_CODEC.encode(buffer, recipe.outputResolver.resolve());
    }
}

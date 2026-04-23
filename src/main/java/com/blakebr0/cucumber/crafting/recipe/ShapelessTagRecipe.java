package com.blakebr0.cucumber.crafting.recipe;

import com.blakebr0.cucumber.crafting.OutputResolver;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
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
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.ShapelessCraftingRecipeDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.Nullable;

import java.util.List;

public class ShapelessTagRecipe extends NormalCraftingRecipe {
    public static final MapCodec<ShapelessTagRecipe> MAP_CODEC = RecordCodecBuilder.mapCodec(builder ->
            builder.group(
                    Recipe.CommonInfo.MAP_CODEC.forGetter(o -> o.commonInfo),
                    CraftingRecipe.CraftingBookInfo.MAP_CODEC.forGetter(o -> o.bookInfo),
                    OutputResolver.Tag.MAP_CODEC.fieldOf("result").forGetter(recipe -> (OutputResolver.Tag) recipe.outputResolver),
                    Ingredient.CODEC.listOf().fieldOf("ingredients").forGetter(o -> o.ingredients)
            ).apply(builder, ShapelessTagRecipe::new)
    );
    public static final StreamCodec<RegistryFriendlyByteBuf, ShapelessTagRecipe> STREAM_CODEC = StreamCodec.of(
            ShapelessTagRecipe::toNetwork, ShapelessTagRecipe::fromNetwork
    );
    public static final RecipeSerializer<ShapelessTagRecipe> SERIALIZER = new RecipeSerializer<>(MAP_CODEC, STREAM_CODEC);

    private final Recipe.CommonInfo commonInfo;
    private final CraftingRecipe.CraftingBookInfo bookInfo;
    private final OutputResolver outputResolver;
    private final List<Ingredient> ingredients;
    private final boolean isSimple;
    private @Nullable ItemStackTemplate result;

    public ShapelessTagRecipe(CommonInfo commonInfo, CraftingBookInfo craftingBookInfo, OutputResolver outputResolver, List<Ingredient> ingredients) {
        super(commonInfo, craftingBookInfo);
        this.commonInfo = commonInfo;
        this.bookInfo = craftingBookInfo;
        this.outputResolver = outputResolver;
        this.ingredients = ingredients;
        this.isSimple = ingredients.stream().allMatch(Ingredient::isSimple);
    }

    @Override
    public ItemStack assemble(CraftingInput input) {
        if (this.result == null) {
            this.result = this.outputResolver.resolve();
        }

        return this.result.create();
    }

    @Override
    public boolean matches(CraftingInput input, Level level) {
        if (input.ingredientCount() != this.ingredients.size()) {
            return false;
        } else if (!isSimple) {
            var nonEmptyItems = new java.util.ArrayList<ItemStack>(input.ingredientCount());
            for (var item : input.items())
                if (!item.isEmpty())
                    nonEmptyItems.add(item);
            return net.neoforged.neoforge.common.util.RecipeMatcher.findMatches(nonEmptyItems, this.ingredients) != null;
        } else {
            return input.size() == 1 && this.ingredients.size() == 1
                    ? this.ingredients.getFirst().test(input.getItem(0))
                    : input.stackedContents().canCraft(this, null);
        }
    }

    @Override
    public RecipeSerializer<ShapelessTagRecipe> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public PlacementInfo createPlacementInfo() {
        return PlacementInfo.create(this.ingredients);
    }

    /**
     * {@return the result of this shapeless recipe or null if it is not static and needs ot be obtained by assembling it}
     */
    public @Nullable ItemStackTemplate result() {
        return null;
    }

    @Override
    public List<RecipeDisplay> display() {
        var result = this.outputResolver.resolve();

        return List.of(
                new ShapelessCraftingRecipeDisplay(
                        this.ingredients.stream().map(Ingredient::display).toList(),
                        new SlotDisplay.ItemStackSlotDisplay(result),
                        new SlotDisplay.ItemSlotDisplay(Items.CRAFTING_TABLE)
                )
        );
    }

    private static ShapelessTagRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
        var commonInfo = CommonInfo.STREAM_CODEC.decode(buffer);
        var craftingBookInfo = CraftingBookInfo.STREAM_CODEC.decode(buffer);
        var ingredients = Ingredient.CONTENTS_STREAM_CODEC.apply(ByteBufCodecs.list()).decode(buffer);
        var result = OutputResolver.create(buffer);

        return new ShapelessTagRecipe(commonInfo, craftingBookInfo, result, ingredients);
    }

    private static void toNetwork(RegistryFriendlyByteBuf buffer, ShapelessTagRecipe recipe) {
        CommonInfo.STREAM_CODEC.encode(buffer, recipe.commonInfo);
        CraftingBookInfo.STREAM_CODEC.encode(buffer, recipe.bookInfo);
        Ingredient.CONTENTS_STREAM_CODEC.apply(ByteBufCodecs.list()).encode(buffer, recipe.ingredients);
        ItemStackTemplate.STREAM_CODEC.encode(buffer, recipe.outputResolver.resolve());
    }
}

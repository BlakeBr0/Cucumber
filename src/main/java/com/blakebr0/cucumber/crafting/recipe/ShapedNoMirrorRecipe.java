package com.blakebr0.cucumber.crafting.recipe;

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

import java.util.List;

public class ShapedNoMirrorRecipe extends NormalCraftingRecipe {
    public static final MapCodec<ShapedNoMirrorRecipe> MAP_CODEC = RecordCodecBuilder.mapCodec(builder ->
            builder.group(
                    Recipe.CommonInfo.MAP_CODEC.forGetter(o -> o.commonInfo),
                    CraftingRecipe.CraftingBookInfo.MAP_CODEC.forGetter(o -> o.bookInfo),
                    ShapedRecipePattern.MAP_CODEC.forGetter(o -> o.pattern),
                    ItemStackTemplate.CODEC.fieldOf("result").forGetter(recipe -> recipe.result)
            ).apply(builder, ShapedNoMirrorRecipe::new)
    );
    public static final StreamCodec<RegistryFriendlyByteBuf, ShapedNoMirrorRecipe> STREAM_CODEC = StreamCodec.of(
            ShapedNoMirrorRecipe::toNetwork, ShapedNoMirrorRecipe::fromNetwork
    );
    public static final RecipeSerializer<ShapedNoMirrorRecipe> SERIALIZER = new RecipeSerializer<>(MAP_CODEC, STREAM_CODEC);

    private final ShapedRecipePattern pattern;
    private final ItemStackTemplate result;

    public ShapedNoMirrorRecipe(CommonInfo commonInfo, CraftingBookInfo craftingBookInfo, ShapedRecipePattern pattern, ItemStackTemplate result) {
        super(commonInfo, craftingBookInfo);
        this.pattern = pattern;
        this.result = result;
    }

    @Override
    public ItemStack assemble(CraftingInput input) {
        return this.result.create();
    }

    @Override
    public boolean matches(CraftingInput inventory, Level level) {
        for (int i = 0; i <= inventory.width() - this.pattern.width(); i++) {
            for (int j = 0; j <= inventory.height() - this.pattern.height(); j++) {
                if (this.checkMatch(inventory, i, j)) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public RecipeSerializer<ShapedNoMirrorRecipe> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public List<RecipeDisplay> display() {
        return List.of(
                new ShapedCraftingRecipeDisplay(
                        this.pattern.width(),
                        this.pattern.height(),
                        this.pattern.ingredients().stream().map(e -> e.map(Ingredient::display).orElse(SlotDisplay.Empty.INSTANCE)).toList(),
                        new SlotDisplay.ItemStackSlotDisplay(this.result),
                        new SlotDisplay.ItemSlotDisplay(Items.CRAFTING_TABLE)
                )
        );
    }

    @Override
    public PlacementInfo createPlacementInfo() {
        return PlacementInfo.createFromOptionals(this.pattern.ingredients());
    }

    public int getWidth() {
        return this.pattern.width();
    }

    public int getHeight() {
        return this.pattern.height();
    }

    private boolean checkMatch(CraftingInput inventory, int x, int y) {
        for (var i = 0; i < inventory.width(); i++) {
            for (var j = 0; j < inventory.height(); j++) {
                var k = i - x;
                var l = j - y;

                if (k >= 0 && l >= 0 && k < this.pattern.width() && l < this.pattern.height()) {
                    var ingredient = this.pattern.ingredients().get(k + l * this.pattern.width());
                    if (ingredient.isPresent()) {
                        if (!ingredient.get().test(inventory.getItem(i + j * inventory.width()))) {
                            return false;
                        }
                    }
                }
            }
        }

        return true;
    }

    private static ShapedNoMirrorRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
        var commonInfo = CommonInfo.STREAM_CODEC.decode(buffer);
        var craftingBookInfo = CraftingBookInfo.STREAM_CODEC.decode(buffer);
        var pattern = ShapedRecipePattern.STREAM_CODEC.decode(buffer);
        var result = ItemStackTemplate.STREAM_CODEC.decode(buffer);

        return new ShapedNoMirrorRecipe(commonInfo, craftingBookInfo, pattern, result);
    }

    private static void toNetwork(RegistryFriendlyByteBuf buffer, ShapedNoMirrorRecipe recipe) {
        CommonInfo.STREAM_CODEC.encode(buffer, recipe.commonInfo);
        CraftingBookInfo.STREAM_CODEC.encode(buffer, recipe.bookInfo);
        ShapedRecipePattern.STREAM_CODEC.encode(buffer, recipe.pattern);
        ItemStackTemplate.STREAM_CODEC.encode(buffer, recipe.result);
    }
}

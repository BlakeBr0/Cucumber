package com.blakebr0.cucumber.crafting.recipe;

import com.mojang.serialization.Codec;
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

public class ShapedTransferComponentsRecipe extends NormalCraftingRecipe {
    public static final MapCodec<ShapedTransferComponentsRecipe> MAP_CODEC = RecordCodecBuilder.mapCodec(builder ->
            builder.group(
                    Recipe.CommonInfo.MAP_CODEC.forGetter(o -> o.commonInfo),
                    CraftingRecipe.CraftingBookInfo.MAP_CODEC.forGetter(o -> o.bookInfo),
                    ShapedRecipePattern.MAP_CODEC.forGetter(o -> o.pattern),
                    ItemStackTemplate.CODEC.fieldOf("result").forGetter(recipe -> recipe.result),
                    Codec.INT.fieldOf("transfer_slot").forGetter(recipe -> recipe.transferSlot)
            ).apply(builder, ShapedTransferComponentsRecipe::new)
    );
    public static final StreamCodec<RegistryFriendlyByteBuf, ShapedTransferComponentsRecipe> STREAM_CODEC = StreamCodec.of(
            ShapedTransferComponentsRecipe::toNetwork, ShapedTransferComponentsRecipe::fromNetwork
    );
    public static final RecipeSerializer<ShapedTransferComponentsRecipe> SERIALIZER = new RecipeSerializer<>(MAP_CODEC, STREAM_CODEC);

    private final ShapedRecipePattern pattern;
    private final ItemStackTemplate result;
    private final int transferSlot;

    public ShapedTransferComponentsRecipe(CommonInfo commonInfo, CraftingBookInfo craftingBookInfo, ShapedRecipePattern pattern, ItemStackTemplate result, int transferSlot) {
        super(commonInfo, craftingBookInfo);
        this.pattern = pattern;
        this.result = result;
        this.transferSlot = transferSlot;
    }

    @Override
    public ItemStack assemble(CraftingInput input) {
        var stack = input.getItem(this.transferSlot);
        return this.result.apply(stack.getComponentsPatch());
    }

    @Override
    public boolean matches(CraftingInput input, Level level) {
        return this.pattern.matches(input);
    }

    @Override
    public RecipeSerializer<ShapedTransferComponentsRecipe> getSerializer() {
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

    private static ShapedTransferComponentsRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
        var commonInfo = CommonInfo.STREAM_CODEC.decode(buffer);
        var craftingBookInfo = CraftingBookInfo.STREAM_CODEC.decode(buffer);
        var pattern = ShapedRecipePattern.STREAM_CODEC.decode(buffer);
        var result = ItemStackTemplate.STREAM_CODEC.decode(buffer);
        var transferSlot = buffer.readVarInt();

        return new ShapedTransferComponentsRecipe(commonInfo, craftingBookInfo, pattern, result, transferSlot);
    }

    private static void toNetwork(RegistryFriendlyByteBuf buffer, ShapedTransferComponentsRecipe recipe) {
        CommonInfo.STREAM_CODEC.encode(buffer, recipe.commonInfo);
        CraftingBookInfo.STREAM_CODEC.encode(buffer, recipe.bookInfo);
        ShapedRecipePattern.STREAM_CODEC.encode(buffer, recipe.pattern);
        ItemStackTemplate.STREAM_CODEC.encode(buffer, recipe.result);
        buffer.writeVarInt(recipe.transferSlot);
    }
}

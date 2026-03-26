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

public class ShapedTransferDamageRecipe extends NormalCraftingRecipe {
    public static final MapCodec<ShapedTransferDamageRecipe> MAP_CODEC = RecordCodecBuilder.mapCodec(builder ->
            builder.group(
                    Recipe.CommonInfo.MAP_CODEC.forGetter(recipe -> recipe.commonInfo),
                    CraftingRecipe.CraftingBookInfo.MAP_CODEC.forGetter(recipe -> recipe.bookInfo),
                    ShapedRecipePattern.MAP_CODEC.forGetter(recipe -> recipe.pattern),
                    ItemStackTemplate.CODEC.fieldOf("result").forGetter(recipe -> recipe.result),
                    Codec.BOOL.fieldOf("transfer_components").forGetter(recipe -> recipe.transferComponents)
            ).apply(builder, ShapedTransferDamageRecipe::new)
    );
    public static final StreamCodec<RegistryFriendlyByteBuf, ShapedTransferDamageRecipe> STREAM_CODEC = StreamCodec.of(
            ShapedTransferDamageRecipe::toNetwork, ShapedTransferDamageRecipe::fromNetwork
    );
    public static final RecipeSerializer<ShapedTransferDamageRecipe> SERIALIZER = new RecipeSerializer<>(MAP_CODEC, STREAM_CODEC);

    private final ShapedRecipePattern pattern;
    private final ItemStackTemplate result;
    private final boolean transferComponents;

    public ShapedTransferDamageRecipe(CommonInfo commonInfo, CraftingBookInfo craftingBookInfo, ShapedRecipePattern pattern, ItemStackTemplate result, boolean transferComponents) {
        super(commonInfo, craftingBookInfo);
        this.pattern = pattern;
        this.result = result;
        this.transferComponents = transferComponents;
    }

    @Override
    public ItemStack assemble(CraftingInput input) {
        var damageable = ItemStack.EMPTY;

        for (var i = 0; i < input.size(); i++) {
            var slotStack = input.getItem(i);

            if (slotStack.isDamageableItem()) {
                damageable = slotStack;
                break;
            }
        }

        var result = this.result.create();

        if (damageable.isEmpty())
            return result;

        if (this.transferComponents) {
            result.applyComponents(damageable.getComponentsPatch());
        } else {
            result.setDamageValue(damageable.getDamageValue());
        }

        return result;
    }

    @Override
    public boolean matches(CraftingInput input, Level level) {
        return this.pattern.matches(input);
    }

    @Override
    public RecipeSerializer<ShapedTransferDamageRecipe> getSerializer() {
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

    private static ShapedTransferDamageRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
        var commonInfo = CommonInfo.STREAM_CODEC.decode(buffer);
        var craftingBookInfo = CraftingBookInfo.STREAM_CODEC.decode(buffer);
        var pattern = ShapedRecipePattern.STREAM_CODEC.decode(buffer);
        var result = ItemStackTemplate.STREAM_CODEC.decode(buffer);
        var transferComponents = buffer.readBoolean();

        return new ShapedTransferDamageRecipe(commonInfo, craftingBookInfo, pattern, result, transferComponents);
    }

    private static void toNetwork(RegistryFriendlyByteBuf buffer, ShapedTransferDamageRecipe recipe) {
        CommonInfo.STREAM_CODEC.encode(buffer, recipe.commonInfo);
        CraftingBookInfo.STREAM_CODEC.encode(buffer, recipe.bookInfo);
        ShapedRecipePattern.STREAM_CODEC.encode(buffer, recipe.pattern);
        ItemStackTemplate.STREAM_CODEC.encode(buffer, recipe.result);
        buffer.writeBoolean(recipe.transferComponents);
    }
}

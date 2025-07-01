package com.blakebr0.cucumber.crafting.ingredient;

import com.blakebr0.cucumber.init.ModIngredientTypes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.crafting.ICustomIngredient;
import net.neoforged.neoforge.common.crafting.IngredientType;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class IngredientWithCount implements ICustomIngredient, Predicate<ItemStack> {
    public static final IngredientWithCount EMPTY = new IngredientWithCount(new Ingredient.ItemValue(ItemStack.EMPTY), 0);
    public static final MapCodec<IngredientWithCount> MAP_CODEC = RecordCodecBuilder.mapCodec(builder ->
            builder.group(
                    Ingredient.Value.MAP_CODEC.xmap(v -> new Ingredient.Value[] { v }, v -> v[0]).forGetter(ingredient -> ingredient.values),
                    Codec.INT.fieldOf("count").forGetter(ingredient -> ingredient.count)
            ).apply(builder, IngredientWithCount::new)
    );
    public static final Codec<IngredientWithCount> CODEC = MAP_CODEC.codec();
    public static final StreamCodec<RegistryFriendlyByteBuf, IngredientWithCount> STREAM_CODEC = StreamCodec.of(
            (buffer, ingredient) -> {
                var items = ingredient.getItems().toList();

                buffer.writeVarInt(items.size());

                for (var item : items) {
                    ItemStack.STREAM_CODEC.encode(buffer, item);
                }

                buffer.writeVarInt(ingredient.count);
            },
            buffer -> {
                var size = buffer.readVarInt();
                var items = NonNullList.withSize(size, ItemStack.EMPTY);

                for (var i = 0; i < size; i++) {
                    items.set(i, ItemStack.STREAM_CODEC.decode(buffer));
                }

                var count = buffer.readVarInt();

                return new IngredientWithCount(items.stream().map(Ingredient.ItemValue::new).toArray(Ingredient.Value[]::new), count);
            }
    );

    private final Ingredient.Value[] values;
    private final int count;

    private ItemStack[] stacks;

    public IngredientWithCount(Ingredient.Value value, int count) {
        this(new Ingredient.Value[] { value }, count);
    }

    public IngredientWithCount(Ingredient.Value[] values, int count) {
        this.values = values;
        this.count = count;
    }

    @Override
    public boolean test(@Nullable ItemStack stack) {
        if (stack == null)
            return false;

        if (this.stacks == null) {
            this.stacks = Arrays.stream(this.values).flatMap(v -> v.getItems().stream()).toArray(ItemStack[]::new);
        }

        return stack.getCount() >= this.count && Arrays.stream(this.stacks).anyMatch(s -> s.is(stack.getItem()));
    }

    @Override
    public Stream<ItemStack> getItems() {
        if (this.stacks == null) {
            this.stacks = Arrays.stream(this.values).flatMap(v -> v.getItems().stream()).toArray(ItemStack[]::new);
        }

        return Stream.of(this.stacks);
    }

    @Override
    public boolean isSimple() {
        return false;
    }

    @Override
    public IngredientType<?> getType() {
        return ModIngredientTypes.WITH_COUNT.get();
    }

    public int getCount() {
        return this.count;
    }

    public static Ingredient of(ItemStack item, int count) {
        return new IngredientWithCount(new Ingredient.ItemValue(item), count).toVanilla();
    }
}

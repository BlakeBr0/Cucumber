package com.blakebr0.cucumber.crafting;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;

public interface OutputResolver {
    @Deprecated(forRemoval = true)
    Codec<ItemStackTemplate> RESULT_CODEC = Codec
            .xor(ItemStackTemplate.CODEC, OutputResolver.Tag.CODEC)
            .xmap(either -> either.map(stack -> stack, Tag::resolve), Either::left);

    Codec<OutputResolver> RESOLVER_CODEC = Codec
            .xor(ItemStackTemplate.CODEC, OutputResolver.Tag.CODEC)
            .xmap(either -> either.map(Item::new, resolver -> resolver), resolver -> {
                if (resolver instanceof Tag tag) {
                    return Either.right(tag);
                } else if (resolver instanceof Item item) {
                    return Either.left(item.stack);
                }

                var stack = new ItemStack(Items.BARRIER);
                stack.set(DataComponents.CUSTOM_NAME, Component.literal("Invalid result"));

                return Either.left(ItemStackTemplate.fromNonEmptyStack(stack));
            });

    ItemStackTemplate resolve();

    static OutputResolver.Item create(RegistryFriendlyByteBuf buffer) {
        return new Item(ItemStackTemplate.STREAM_CODEC.decode(buffer));
    }

    class Tag implements OutputResolver {
        public static final MapCodec<Tag> MAP_CODEC = RecordCodecBuilder.mapCodec(builder ->
                builder.group(
                        Codec.STRING.fieldOf("tag").forGetter(result -> result.tag),
                        Codec.INT.fieldOf("count").forGetter(result -> result.count)
                ).apply(builder, Tag::new)
        );
        public static final Codec<Tag> CODEC = MAP_CODEC.codec();
        public static final StreamCodec<RegistryFriendlyByteBuf, Tag> STREAM_CODEC = StreamCodec.composite(
                ByteBufCodecs.STRING_UTF8,
                t -> t.tag,
                ByteBufCodecs.INT,
                t -> t.count,
                Tag::new
        );

        private final String tag;
        private final int count;

        public Tag(String tag, int count) {
            this.tag = tag;
            this.count = count;
        }

        @Override
        public ItemStackTemplate resolve() {
            return TagMapper.getItemStackForTag(this.tag, this.count);
        }
    }

    class Item implements OutputResolver {
        private final ItemStackTemplate stack;

        public Item(ItemStackTemplate stack) {
            this.stack = stack;
        }

        @Override
        public ItemStackTemplate resolve() {
            return this.stack;
        }
    }
}

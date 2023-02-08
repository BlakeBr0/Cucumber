package com.blakebr0.cucumber.crafting.recipe;

import com.blakebr0.cucumber.crafting.TagMapper;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;

public interface OutputResolver {
    ItemStack resolve();

    static OutputResolver.Item create(FriendlyByteBuf buffer) {
        return new Item(buffer.readItem());
    }

    class Tag implements OutputResolver {
        private final String tag;
        private final int count;

        public Tag(String tag, int count) {
            this.tag = tag;
            this.count = count;
        }

        @Override
        public ItemStack resolve() {
            return TagMapper.getItemStackForTag(this.tag, this.count);
        }
    }

    class Item implements OutputResolver {
        private final ItemStack stack;

        public Item(ItemStack stack) {
            this.stack = stack;
        }

        @Override
        public ItemStack resolve() {
            return this.stack;
        }
    }
}

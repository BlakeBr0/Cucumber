package com.blakebr0.cucumber.iface;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.color.block.BlockTintSource;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;

public interface IColored {
	default int getColor(int index) {
		return -1;
	}

	default int getColor(int index, ItemStack stack) {
		return this.getColor(index);
	}

	record BlockColors() implements BlockTintSource {
		@Override
		public int color(BlockState state) {
			return ((IColored) state.getBlock()).getColor(0);
		}
	}

	record ItemColors(int index) implements ItemTintSource {
		public static final MapCodec<ItemColors> MAP_CODEC = RecordCodecBuilder.mapCodec(builder ->
				builder.group(
						Codec.INT.fieldOf("index").forGetter(ItemColors::index)
				).apply(builder, ItemColors::new)
		);

		@Override
		public int calculate(ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity owner) {
			return ((IColored) stack.getItem()).getColor(this.index, stack);
		}

		@Override
		public MapCodec<? extends ItemTintSource> type() {
			return MAP_CODEC;
		}
	}

	record ItemBlockColors(int index) implements ItemTintSource {
		public static final MapCodec<ItemBlockColors> MAP_CODEC = RecordCodecBuilder.mapCodec(builder ->
				builder.group(
						Codec.INT.fieldOf("index").forGetter(ItemBlockColors::index)
				).apply(builder, ItemBlockColors::new)
		);

		@Override
		public int calculate(ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity owner) {
			return ((IColored) Block.byItem(stack.getItem())).getColor(this.index, stack);
		}

		@Override
		public MapCodec<? extends ItemTintSource> type() {
			return MAP_CODEC;
		}
	}
}

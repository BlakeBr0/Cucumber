package com.blakebr0.cucumber.helper;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;
import java.util.stream.Stream;

public final class FluidHelper {
	public static FluidStack getFluidFromStack(ItemStack stack) {
		return FluidStack.loadFluidStackFromNBT(stack.getTag());
	}

	public static Rarity getFluidRarity(FluidStack fluid) {
		return fluid.getFluid().getFluidType().getRarity();
	}

	public static int getFluidAmount(ItemStack stack) {
		var fluid = getFluidFromStack(stack);
		return fluid == null ? 0 : fluid.getAmount();
	}

	public static ItemStack getFilledBucket(FluidStack fluid, Item bucket, int capacity) {
		if (ForgeRegistries.FLUIDS.containsValue(fluid.getFluid())) {
			var filledBucket = new ItemStack(bucket);
			var fluidContents = new FluidStack(fluid, capacity);

			var tag = new CompoundTag();

			fluidContents.writeToNBT(tag);
			filledBucket.setTag(tag);

			return filledBucket;
		}

		return ItemStack.EMPTY;
	}

	public static int toBuckets(int i) {
		return i - (i % 1000);
	}

	public static Map<Fluid, List<ResourceLocation>> getFluidTags(ItemStack stack) {
		return stack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).map(fluidHandler -> {
			var fluidTagsMap = new LinkedHashMap<Fluid, List<ResourceLocation>>();
			int tanks = fluidHandler.getTanks();

			for (int i = 0; i < tanks; i++) {
				var fluidStack = fluidHandler.getFluidInTank(i);
				if (!fluidStack.isEmpty()) {
					var fluid = fluidStack.getFluid();
					fluidTagsMap.computeIfAbsent(fluid, f ->
							ForgeRegistries.FLUIDS.getHolder(f)
									.map(Holder::tags)
									.orElse(Stream.empty())
									.map(TagKey::location)
									.sorted(Comparator.comparing(ResourceLocation::toString))
									.toList()
					);
				}
			}
			return fluidTagsMap;
		}).orElse(new LinkedHashMap<>());
	}
}

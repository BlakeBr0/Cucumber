package com.blakebr0.cucumber.helper;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.transfer.access.ItemAccess;
import net.neoforged.neoforge.transfer.fluid.FluidResource;
import net.neoforged.neoforge.transfer.transaction.Transaction;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class FluidHelper {
	public static ItemStack getFilledBucket(FluidStack fluid, Item bucket, int capacity) {
		if (BuiltInRegistries.FLUID.containsValue(fluid.getFluid())) {
			var filledBucket = new ItemStack(bucket);

			var tank = ItemAccess.forStack(filledBucket).getCapability(Capabilities.Fluid.ITEM);
			if (tank != null) {
				try (var tx = Transaction.openRoot()) {
					tank.insert(FluidResource.of(fluid), capacity, tx);
					tx.commit();
				}
			}

			return filledBucket;
		}

		return ItemStack.EMPTY;
	}

	public static int toBuckets(int i) {
		return i - (i % 1000);
	}

	public static Map<Fluid, List<Identifier>> getFluidTags(ItemStack stack) {
		var fluidHandler = ItemAccess.forStack(stack).getCapability(Capabilities.Fluid.ITEM);
		if (fluidHandler == null) {
			return Map.of();
		}

		var fluidTagsMap = new LinkedHashMap<Fluid, List<Identifier>>();
		int tanks = fluidHandler.size();

		for (int i = 0; i < tanks; i++) {
			var fluidStack = fluidHandler.getResource(i);
			if (!fluidStack.isEmpty()) {
				var fluid = fluidStack.getFluid();

				fluidTagsMap.computeIfAbsent(fluid, f ->
						BuiltInRegistries.FLUID.getResourceKey(f)
						.flatMap(BuiltInRegistries.FLUID::get)
						.map(holder -> holder.tags()
								.map(TagKey::location)
								.sorted(Comparator.comparing(Identifier::toString))
								.toList())
						.orElse(List.of())
				);
			}
		}

		return fluidTagsMap;
	}
}

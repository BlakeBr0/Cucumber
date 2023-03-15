package com.blakebr0.cucumber.util;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

import java.util.Arrays;
import java.util.function.Supplier;

@FunctionalInterface
public interface FeatureFlagDisplayItemGenerator {
    void accept(CreativeModeTab.ItemDisplayParameters parameters, Output output);

    static CreativeModeTab.DisplayItemsGenerator create(FeatureFlagDisplayItemGenerator generator) {
        return (parameters, output) -> generator.accept(parameters, Output.from(output));
    }

    interface Output extends CreativeModeTab.Output {
        static Output from(CreativeModeTab.Output output) {
            return output::accept;
        }

        default void accept(ItemStack stack, FeatureFlag... flags) {
            if (Arrays.stream(flags).allMatch(FeatureFlag::isEnabled)) {
                this.accept(stack);
            }
        }

        default void accept(Supplier<? extends ItemLike> item) {
            this.accept(new ItemStack(item.get()));
        }

        default void accept(Supplier<? extends ItemLike> item, FeatureFlag... flags) {
            if (Arrays.stream(flags).allMatch(FeatureFlag::isEnabled)) {
                this.accept(new ItemStack(item.get()));
            }
        }
    }
}

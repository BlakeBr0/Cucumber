package com.blakebr0.cucumber.util;

import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

import java.util.function.Supplier;

@FunctionalInterface
public interface FeatureFlagDisplayItemGenerator extends CreativeModeTab.DisplayItemsGenerator {
    void accept(FeatureFlagSet flagSet, Output output, boolean hasPermission);

    @Override
    default void accept(FeatureFlagSet flagSet, CreativeModeTab.Output output, boolean hasPermission) {
        this.accept(flagSet, Output.from(output), hasPermission);
    }

    static CreativeModeTab.DisplayItemsGenerator create(FeatureFlagDisplayItemGenerator generator) {
        return generator;
    }

    interface Output extends CreativeModeTab.Output {
        static Output from(CreativeModeTab.Output output) {
            return output::accept;
        }

        default void accept(ItemStack stack, FeatureFlag flag) {
            if (flag.isEnabled()) {
                this.accept(stack);
            }
        }

        default void accept(Supplier<? extends ItemLike> item) {
            this.accept(new ItemStack(item.get()));
        }

        default void accept(Supplier<? extends ItemLike> item, FeatureFlag flag) {
            if (flag.isEnabled()) {
                this.accept(new ItemStack(item.get()), flag);
            }
        }
    }
}

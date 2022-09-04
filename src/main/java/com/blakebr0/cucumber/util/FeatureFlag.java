package com.blakebr0.cucumber.util;

import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.function.Supplier;

public class FeatureFlag {
    private static final HashMap<ResourceLocation, FeatureFlag> REGISTRY = new HashMap<>();
    private static final FeatureFlag NONE = new FeatureFlag(null, () -> false);

    private final ResourceLocation id;
    private Supplier<Boolean> condition;

    private FeatureFlag(ResourceLocation id, Supplier<Boolean> condition) {
        this.id = id;
        this.condition = condition;
    }

    public static FeatureFlag create(ResourceLocation id, Supplier<Boolean> condition) {
        var flag = new FeatureFlag(id, condition);
        REGISTRY.put(id, flag);
        return flag;
    }

    public static FeatureFlag from(ResourceLocation id) {
        return new FeatureFlag(id, null);
    }

    public ResourceLocation getId() {
        return this.id;
    }

    public boolean isEnabled() {
        if (this.condition == null) {
            this.condition = REGISTRY.getOrDefault(this.id, NONE).condition;
        }

        return this.condition.get();
    }
}

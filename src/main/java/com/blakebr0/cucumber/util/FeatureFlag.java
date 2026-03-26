package com.blakebr0.cucumber.util;

import net.minecraft.resources.Identifier;

import java.util.HashMap;
import java.util.function.Supplier;

public class FeatureFlag {
    private static final HashMap<Identifier, FeatureFlag> REGISTRY = new HashMap<>();
    private static final FeatureFlag NONE = new FeatureFlag(null, () -> false);

    private final Identifier id;
    private Supplier<Boolean> condition;

    private FeatureFlag(Identifier id, Supplier<Boolean> condition) {
        this.id = id;
        this.condition = condition;
    }

    public static FeatureFlag create(Identifier id, Supplier<Boolean> condition) {
        var flag = new FeatureFlag(id, condition);
        REGISTRY.put(id, flag);
        return flag;
    }

    public static FeatureFlag from(Identifier id) {
        return new FeatureFlag(id, null);
    }

    public Identifier getId() {
        return this.id;
    }

    public boolean isEnabled() {
        if (this.condition == null) {
            this.condition = REGISTRY.getOrDefault(this.id, NONE).condition;
        }

        return this.condition.get();
    }
}

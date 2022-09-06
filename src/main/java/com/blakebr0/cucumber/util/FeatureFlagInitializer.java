package com.blakebr0.cucumber.util;

import com.blakebr0.cucumber.Cucumber;
import net.minecraftforge.fml.ModList;

public final class FeatureFlagInitializer {
    public static void init() {
        ModList.get().getAllScanData().forEach(data -> {
            data.getAnnotations().forEach(annotation -> {
                if (annotation.annotationType().getClassName().equals(FeatureFlags.class.getName())) {
                    try {
                        Class<?> clazz = Class.forName(annotation.memberName());
                        clazz.getClassLoader().loadClass(clazz.getName());
                    } catch (Exception e) {
                        Cucumber.LOGGER.error("Error loading feature flags: {}", annotation.memberName(), e);
                    }
                }
            });
        });
    }
}

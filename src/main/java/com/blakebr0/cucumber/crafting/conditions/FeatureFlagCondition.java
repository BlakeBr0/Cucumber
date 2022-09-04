package com.blakebr0.cucumber.crafting.conditions;

import com.blakebr0.cucumber.Cucumber;
import com.blakebr0.cucumber.util.FeatureFlag;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionSerializer;

public record FeatureFlagCondition(ResourceLocation flag) implements ICondition {
    private static final ResourceLocation ID = new ResourceLocation(Cucumber.MOD_ID, "feature_flag");

    @Override
    public ResourceLocation getID() {
        return ID;
    }

    @Override
    public boolean test(IContext context) {
        var flag = FeatureFlag.from(this.flag);
        return flag.isEnabled();
    }

    public static class Serializer implements IConditionSerializer<FeatureFlagCondition> {
        public static final Serializer INSTANCE = new Serializer();

        @Override
        public void write(JsonObject json, FeatureFlagCondition value) {
            json.addProperty("flag", value.flag.toString());
        }

        @Override
        public FeatureFlagCondition read(JsonObject json) {
            return new FeatureFlagCondition(new ResourceLocation(GsonHelper.getAsString(json, "flag")));
        }

        @Override
        public ResourceLocation getID() {
            return FeatureFlagCondition.ID;
        }
    }
}

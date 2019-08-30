package com.blakebr0.cucumber.crafting.conditions;

import com.blakebr0.cucumber.Cucumber;
import com.blakebr0.cucumber.iface.IEnableable;
import com.google.gson.JsonObject;
import net.minecraft.item.Item;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionSerializer;
import net.minecraftforge.registries.ForgeRegistries;

public class EnableableCondition implements ICondition {
    private static final ResourceLocation ID = new ResourceLocation(Cucumber.MOD_ID, "enabled");
    private final ResourceLocation item;

    public EnableableCondition(ResourceLocation item) {
        this.item = item;
    }

    @Override
    public ResourceLocation getID() {
        return ID;
    }

    @Override
    public boolean test() {
        Item item = ForgeRegistries.ITEMS.getValue(this.item);
        if (item == null) return false;
        return !(item instanceof IEnableable) || ((IEnableable) item).isEnabled();
    }

    public static class Serializer implements IConditionSerializer<EnableableCondition> {
        public static final Serializer INSTANCE = new Serializer();

        @Override
        public void write(JsonObject json, EnableableCondition value) {
            json.addProperty("item", value.item.toString());
        }

        @Override
        public EnableableCondition read(JsonObject json) {
            return new EnableableCondition(new ResourceLocation(JSONUtils.getString(json, "item")));
        }

        @Override
        public ResourceLocation getID() {
            return EnableableCondition.ID;
        }
    }
}

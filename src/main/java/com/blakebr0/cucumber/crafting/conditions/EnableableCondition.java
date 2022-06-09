package com.blakebr0.cucumber.crafting.conditions;

import com.blakebr0.cucumber.Cucumber;
import com.blakebr0.cucumber.iface.IEnableable;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionSerializer;
import net.minecraftforge.registries.ForgeRegistries;

public record EnableableCondition(ResourceLocation item) implements ICondition {
    private static final ResourceLocation ID = new ResourceLocation(Cucumber.MOD_ID, "enabled");

    @Override
    public ResourceLocation getID() {
        return ID;
    }

    @Override
    public boolean test(IContext context) {
        var item = ForgeRegistries.ITEMS.getValue(this.item);
        if (item == Items.AIR)
            return false;

        return !(item instanceof IEnableable enableable) || enableable.isEnabled();
    }

    public static class Serializer implements IConditionSerializer<EnableableCondition> {
        public static final Serializer INSTANCE = new Serializer();

        @Override
        public void write(JsonObject json, EnableableCondition value) {
            json.addProperty("item", value.item.toString());
        }

        @Override
        public EnableableCondition read(JsonObject json) {
            return new EnableableCondition(new ResourceLocation(GsonHelper.getAsString(json, "item")));
        }

        @Override
        public ResourceLocation getID() {
            return EnableableCondition.ID;
        }
    }
}

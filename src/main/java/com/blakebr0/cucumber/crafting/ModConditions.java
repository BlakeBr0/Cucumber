package com.blakebr0.cucumber.crafting;

import com.blakebr0.cucumber.Cucumber;
import com.blakebr0.cucumber.iface.IEnableable;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IConditionSerializer;
import net.minecraftforge.registries.ForgeRegistries;

public class ModConditions {
    public static final IConditionSerializer CONDITION_ENABLED;

    static {
        ResourceLocation location = new ResourceLocation(Cucumber.MOD_ID, "enabled");
        CONDITION_ENABLED = CraftingHelper.register(location, json -> {
            String id = json.get("item").getAsString();
            Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(id));
            return () -> !(item instanceof IEnableable) || ((IEnableable) item).isEnabled();
        });
    }
}

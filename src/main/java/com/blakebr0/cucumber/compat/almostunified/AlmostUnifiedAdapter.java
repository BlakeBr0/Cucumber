package com.blakebr0.cucumber.compat.almostunified;

import com.almostreliable.unified.api.AlmostUnifiedLookup;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;

public class AlmostUnifiedAdapter {
    public static boolean isLoaded() {
        return ModList.get().isLoaded("almostunified");
    }

    public static Item getPreferredItemForTag(String tagId) {
        if (isLoaded()) {
            var tagKey = TagKey.create(ForgeRegistries.Keys.ITEMS, new ResourceLocation(tagId));
            return Adapter.getPreferredItemForTag(tagKey);
        }

        return null;
    }

    private static class Adapter {
        private static Item getPreferredItemForTag(TagKey<Item> tag) {
            return AlmostUnifiedLookup.INSTANCE.getPreferredItemForTag(tag);
        }
    }
}

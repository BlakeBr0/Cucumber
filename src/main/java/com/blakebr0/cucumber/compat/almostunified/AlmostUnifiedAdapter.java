package com.blakebr0.cucumber.compat.almostunified;

import com.almostreliable.unified.api.AlmostUnified;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.neoforged.fml.ModList;

public class AlmostUnifiedAdapter {
    public static boolean isLoaded() {
        return ModList.get().isLoaded("almostunified");
    }

    public static Item getPreferredItemForTag(String tagId) {
        if (isLoaded()) {
            return Adapter.getPreferredItemForTag(ItemTags.create(ResourceLocation.parse(tagId)));
        }

        return null;
    }

    private static class Adapter {
        private static Item getPreferredItemForTag(TagKey<Item> tag) {
            return AlmostUnified.INSTANCE.getTagTargetItem(tag);
        }
    }
}

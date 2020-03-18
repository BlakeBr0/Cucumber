package com.blakebr0.cucumber.crafting;

import com.blakebr0.cucumber.Cucumber;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class TagMapper {
    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();
    private static final Logger LOGGER = LogManager.getLogger(Cucumber.NAME);
    private static final Map<String, String> TAG_TO_ITEM_MAP = new HashMap<>();

    public static void reloadTagMappings() {
        File dir = FMLPaths.CONFIGDIR.get().toFile();

        if (dir.exists() && dir.isDirectory()) {
            File file = FMLPaths.CONFIGDIR.get().resolve("cucumber-tags.json").toFile();
            if (file.exists() && file.isFile()) {
                JsonObject json;
                FileReader reader = null;
                try {
                    JsonParser parser = new JsonParser();
                    reader = new FileReader(file);
                    json = parser.parse(reader).getAsJsonObject();

                    json.entrySet().stream().filter(e -> !"__comment".equals(e.getKey())).forEach(entry -> {
                        String tag = entry.getKey();
                        String item = entry.getValue().getAsString();

                        TAG_TO_ITEM_MAP.put(tag, item);
                    });

                    reader.close();
                } catch (Exception e) {
                    LOGGER.error("An error occurred while reading cucumber-tags.json", e);
                } finally {
                    IOUtils.closeQuietly(reader);
                }
            } else {
                try (Writer writer = new FileWriter(file)) {
                    JsonObject object = new JsonObject();
                    object.addProperty("__comment", "Instructions: https://mods.blakebr0.com/docs/cucumber/tags-config");
                    GSON.toJson(object, writer);
                } catch (IOException e) {
                    LOGGER.error("An error occurred while creating cucumber-tags.json", e);
                }
            }
        }
    }

    public static Item getItemForTag(String tagId) {
        if (TAG_TO_ITEM_MAP.containsKey(tagId)) {
            String id = TAG_TO_ITEM_MAP.get(tagId);
            return ForgeRegistries.ITEMS.getValue(new ResourceLocation(id));
        } else {
            File file = FMLPaths.CONFIGDIR.get().resolve("cucumber-tags.json").toFile();

            if (file.exists() && file.isFile()) {
                JsonObject json = null;
                FileReader reader = null;
                try {
                    JsonParser parser = new JsonParser();
                    reader = new FileReader(file);
                    json = parser.parse(reader).getAsJsonObject();
                } catch (Exception e) {
                    LOGGER.error("An error occurred while reading cucumber-tags.json", e);
                } finally {
                    IOUtils.closeQuietly(reader);
                }

                if (json != null) {
                    if (json.has(tagId)) {
                        String itemId = json.get(tagId).getAsString();
                        if (itemId.isEmpty() || "null".equals(itemId))
                            return addTagToFile(tagId, json, file);

                        TAG_TO_ITEM_MAP.put(tagId, itemId);
                        return ForgeRegistries.ITEMS.getValue(new ResourceLocation(itemId));
                    } else {
                        return addTagToFile(tagId, json, file);
                    }
                }
            }

            return Items.AIR;
        }
    }

    private static Item addTagToFile(String tagId, JsonObject json, File file) {
        Tag<Item> tag = ItemTags.getCollection().getOrCreate(new ResourceLocation(tagId));
        Item item = tag.getAllElements().stream().findFirst().orElse(Items.AIR);

        String itemId = "null";
        if (item.getRegistryName() != null && item != Items.AIR) {
            itemId = item.getRegistryName().toString();
        }

        json.addProperty(tagId, itemId);
        TAG_TO_ITEM_MAP.put(tagId, itemId);

        try (Writer writer = new FileWriter(file)) {
            GSON.toJson(json, writer);
        } catch (IOException e) {
            LOGGER.error("An error occurred while writing to cucumber-tags.json", e);
        }

        return item;
    }
}

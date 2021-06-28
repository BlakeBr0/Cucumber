package com.blakebr0.cucumber.crafting;

import com.blakebr0.cucumber.Cucumber;
import com.blakebr0.cucumber.config.ModConfigs;
import com.google.common.base.Stopwatch;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.resources.IResourceManager;
import net.minecraft.resources.IResourceManagerReloadListener;
import net.minecraft.tags.ITag;
import net.minecraft.tags.TagCollectionManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
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
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class TagMapper implements IResourceManagerReloadListener {
    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();
    private static final Logger LOGGER = LogManager.getLogger(Cucumber.NAME);
    private static final Map<String, String> TAG_TO_ITEM_MAP = new HashMap<>();

    @Override
    public void onResourceManagerReload(IResourceManager manager) {
        reloadTagMappings();
    }

    @SubscribeEvent
    public void onAddReloadListeners(AddReloadListenerEvent event) {
        event.addListener(this);
    }

    public static void reloadTagMappings() {
        Stopwatch stopwatch = Stopwatch.createStarted();
        File dir = FMLPaths.CONFIGDIR.get().toFile();

        TAG_TO_ITEM_MAP.clear();

        if (dir.exists() && dir.isDirectory()) {
            File file = FMLPaths.CONFIGDIR.get().resolve("cucumber-tags.json").toFile();
            if (file.exists() && file.isFile()) {
                JsonObject json;
                FileReader reader = null;

                try {
                    JsonParser parser = new JsonParser();
                    reader = new FileReader(file);
                    json = parser.parse(reader).getAsJsonObject();

                    json.entrySet().stream().filter(e -> {
                        String value = e.getValue().getAsString();
                        return !"__comment".equalsIgnoreCase(e.getKey()) && !value.isEmpty() && !"null".equalsIgnoreCase(value);
                    }).forEach(entry -> {
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
                generateNewConfig(file);
            }
        }

        stopwatch.stop();

        LOGGER.info("Loaded cucumber-tags.json in {} ms", stopwatch.elapsed(TimeUnit.MILLISECONDS));
    }

    public static Item getItemForTag(String tagId) {
        if (TAG_TO_ITEM_MAP.containsKey(tagId)) {
            String id = TAG_TO_ITEM_MAP.get(tagId);
            return ForgeRegistries.ITEMS.getValue(new ResourceLocation(id));
        } else {
            File file = FMLPaths.CONFIGDIR.get().resolve("cucumber-tags.json").toFile();

            if (!file.exists()) {
                generateNewConfig(file);
            }

            if (file.isFile()) {
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
                        if (itemId.isEmpty() || "null".equalsIgnoreCase(itemId))
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
        List<String> mods = ModConfigs.MOD_TAG_PRIORITIES.get();
        ITag<Item> tag = TagCollectionManager.getInstance().getItems().getTag(new ResourceLocation(tagId));
        Item item = tag == null ? Items.AIR : tag.getValues().stream().min((item1, item2) -> {
            int index1 = item1.getRegistryName() != null ? mods.indexOf(item1.getRegistryName().getNamespace()) : -1;
            int index2 = item2.getRegistryName() != null ? mods.indexOf(item2.getRegistryName().getNamespace()) : -1;

            return index1 > index2 ? 1 : index1 == -1 ? 0 : -1;
        }).orElse(Items.AIR);

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

    private static void generateNewConfig(File file) {
        try (Writer writer = new FileWriter(file)) {
            JsonObject object = new JsonObject();
            object.addProperty("__comment", "Instructions: https://mods.blakebr0.com/docs/cucumber/tags-config");

            GSON.toJson(object, writer);
        } catch (IOException e) {
            LOGGER.error("An error occurred while creating cucumber-tags.json", e);
        }
    }
}

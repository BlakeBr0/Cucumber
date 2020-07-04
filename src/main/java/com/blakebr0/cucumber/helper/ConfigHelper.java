package com.blakebr0.cucumber.helper;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.loading.FMLPaths;

import java.nio.file.Path;

public final class ConfigHelper {
    public static void load(ForgeConfigSpec config, String location) {
        Path path = FMLPaths.CONFIGDIR.get().resolve(location);
        CommentedFileConfig data = CommentedFileConfig.builder(path).sync().autosave().writingMode(WritingMode.REPLACE).build();

        data.load();
        config.setConfig(data);
    }
}

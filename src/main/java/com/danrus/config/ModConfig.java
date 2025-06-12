package com.danrus.config;

import com.danrus.DestroyItemWhiteList;
import com.danrus.config.categories.SimpleCategory;
import com.google.gson.GsonBuilder;
import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import dev.isxander.yacl3.platform.YACLPlatform;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class ModConfig {
    public static final ConfigClassHandler<ModConfig> HANDLER = ConfigClassHandler.createBuilder(ModConfig.class)
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(YACLPlatform.getConfigDir().resolve(DestroyItemWhiteList.MOD_ID+".json5"))
                    .appendGsonBuilder(GsonBuilder::setPrettyPrinting)
                    .setJson5(true)
                    .build())
            .build();

    public static List<ConfigCategory> CATEGORIES = new ArrayList<>();

    @SerialEntry
    public boolean hideWhiteListedSlots = false;

    @SerialEntry
    public boolean hideWhiteListedItems = false;

    @SerialEntry
    public boolean showWhenBindPressed = false;

    @SerialEntry
    public boolean showWhenDIHovered = false;

    @SerialEntry
    public int clicksForForceDelete = 5;

    @SerialEntry
    public int forceDeleteClickDelay = 5;


    public static Screen getConfigScreen(Screen parent) {
        return YetAnotherConfigLib.createBuilder()
                .title(Text.literal("Destroy Item White List"))
                .categories(CATEGORIES)
                .save(ModConfig::save)
                .build()
                .generateScreen(parent);
    }

    public static void initialize() {
        load();
        CATEGORIES.add(SimpleCategory.getCategory());
    }

    public static ModConfig get() {
        return HANDLER.instance();
    }

    public static void save() {
        HANDLER.save();
    }

    public static void load() {
        HANDLER.load();
    }
}

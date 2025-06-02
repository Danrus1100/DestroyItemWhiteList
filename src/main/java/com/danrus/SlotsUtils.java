package com.danrus;

import com.danrus.config.ModConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.lang.reflect.Type;

public class SlotsUtils {

    public static Set<Integer> whitelistedSlotIds;

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("diwl_items.json");

    public static void init() {
        loadConfig();
        if (whitelistedSlotIds == null) {
            whitelistedSlotIds = new HashSet<Integer>();
            saveConfig();
        }
    }

    public static void loadConfig() {
        File configFile = CONFIG_PATH.toFile();
        if (configFile.exists()) {
            try (FileReader reader = new FileReader(configFile)) {
                Type collectionType = new TypeToken<HashSet<Integer>>(){}.getType();
                Set<Integer> loadedIds = GSON.fromJson(reader, collectionType);
                if (loadedIds != null) {
                    whitelistedSlotIds = new HashSet<Integer>(loadedIds);
                } else {
                    whitelistedSlotIds = new HashSet<Integer>();
                }
            } catch (IOException e) {
                System.err.println("Failed to load diwl_items.json: " + e.getMessage());
                e.printStackTrace();
                whitelistedSlotIds = new HashSet<Integer>();
            }
        } else {
            whitelistedSlotIds = new HashSet<Integer>();
        }
    }


    public static void saveConfig() {
        try (FileWriter writer = new FileWriter(CONFIG_PATH.toFile())) {
            GSON.toJson(whitelistedSlotIds, writer);
        } catch (IOException e) {
            System.err.println("Failed to save diwl_items.json: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void add(int id) {
        whitelistedSlotIds.add(id);
        saveConfig();
    }

    public static void rm(int id) {
        whitelistedSlotIds.remove(id);
        saveConfig();
    }

    public static void toggle(int id) {
        if (whitelistedSlotIds.contains(id)) {
            rm(id);
        } else {
            add(id);
        }
    }

    public static boolean shouldBeDeleted(int id) {
        return !whitelistedSlotIds.contains(id);
    }

    public static boolean shouldBeShowed() {
//        boolean isSettingNotDefault = !ModConfig.get().showWhenDIHoveredOnly && !ModConfig.get().hideWhileBindUp;
//        boolean isShowByBind = !ModConfig.get().hideWhileBindUp || (ModConfig.get().hideWhileBindUp && KeyBindsManager.isDoToggleWhiteListPressed);
        return true; // TODO: im so confused. IDK how i can say my idea for people... And how to check conditions. Fuck
    }
}

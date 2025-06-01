package com.danrus;

import com.danrus.config.ModConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DestroyItemWhiteList implements ClientModInitializer {

    public static final String MOD_ID = "diwl";



    @Override
    public void onInitializeClient() {
        KeyBindsManager.register();
        SlotsUtils.init();
        ModConfig.initialize();

        ClientTickEvents.END_CLIENT_TICK.register(KeyBindsManager::handle);
    }
}

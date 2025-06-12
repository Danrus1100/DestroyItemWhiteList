package com.danrus;

import com.danrus.config.ModConfig;
import com.danrus.slots.ForceDeleteManager;
import com.danrus.slots.SlotsUtils;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public class DestroyItemWhiteList implements ClientModInitializer {

    public static final String MOD_ID = "diwl";



    @Override
    public void onInitializeClient() {
        KeyBindsManager.register();
        SlotsUtils.init();
        ModConfig.initialize();

        ClientTickEvents.END_CLIENT_TICK.register(KeyBindsManager::handle);
        ClientTickEvents.END_CLIENT_TICK.register(ForceDeleteManager::tick);
    }
}

package com.example;

import com.example.config.ModConfig;
import net.fabricmc.api.ClientModInitializer;

public class TemplateMod implements ClientModInitializer {

    public static final String MOD_ID = "template_mod";

    @Override
    public void onInitializeClient() {
        ModConfig.initialize();
    }
}

package com.danrus;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.client.screen.v1.ScreenKeyboardEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class KeyBindsManager {
    public static KeyBinding doToggleWhiteList;
    public static boolean isDoToggleWhiteListPressed = false;

    public static void register() {
        doToggleWhiteList = new KeyBinding(
                "key.diwl.do_toggle_whitelist",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_GRAVE_ACCENT,
                "category.diwl.keybinds"
        );
        KeyBindingHelper.registerKeyBinding(doToggleWhiteList);
    }

    public static void handle(MinecraftClient mc) {

        if (mc.currentScreen instanceof CreativeInventoryScreen) {
            ScreenKeyboardEvents.afterKeyPress(mc.currentScreen).register((screen, keyCode, scanCode, modifiers) -> {
                if (doToggleWhiteList.matchesKey(keyCode, scanCode)) {
                    isDoToggleWhiteListPressed = true;
                }
            });
            ScreenKeyboardEvents.afterKeyRelease(mc.currentScreen).register((screen, keyCode, scanCode, modifiers) -> {
                if (doToggleWhiteList.matchesKey(keyCode, scanCode)) {
                    isDoToggleWhiteListPressed = false;
                }
            });
        }
    }
}

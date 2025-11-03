package com.danrus;

import com.danrus.utils.KeyMapsUtils;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.screen.v1.ScreenKeyboardEvents;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import org.lwjgl.glfw.GLFW;

public class KeyBindsManager {
    public static KeyMapping doToggleWhiteList;
    public static boolean isDoToggleWhiteListPressed = false;

    public static void register() {
        doToggleWhiteList = KeyMapsUtils.create(
                "key.diwl.do_toggle_whitelist",
                GLFW.GLFW_KEY_GRAVE_ACCENT
        );
        KeyBindingHelper.registerKeyBinding(doToggleWhiteList);
    }

    public static void handle(Minecraft mc) {

        if (mc.screen != null) {
            KeyMapsUtils.registerAfterPress(mc.screen, (event) -> {
                if (KeyMapsUtils.matches(doToggleWhiteList, event)) {
                    isDoToggleWhiteListPressed = true;
                }
            });
            KeyMapsUtils.registerAfterRelease(mc.screen, (event) -> {
                if (KeyMapsUtils.matches(doToggleWhiteList, event)) {
                    isDoToggleWhiteListPressed = false;
                }
            });
        }
    }
}

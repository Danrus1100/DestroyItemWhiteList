package com.danrus.utils;

import com.danrus.KeyEventWrapper;
import net.fabricmc.fabric.api.client.screen.v1.ScreenKeyboardEvents;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Consumer;

public class KeyMapsUtils {
    public static KeyMapping create(String name, int keyCode) {
        //? if <=1.21.8 {
        return new KeyMapping(
                name,
                keyCode,
                "key.category.diwl.keybinds"
        );
        //?} else {
        /*KeyMapping.Category category1 = new KeyMapping.Category(ResourceLocation.fromNamespaceAndPath("diwl", "keybinds"));
        return new KeyMapping(
                name,
                keyCode,
                category1
        );
        *///?}
    }

    public static boolean matches(KeyMapping keyMapping, KeyEventWrapper event) {
        //? if <1.21.9 {
        return keyMapping.matches(event.keyCode(), event.scanCode());
        //?} else {
        /*return keyMapping.matches(event.event());
        *///?}
    }

    public static void registerAfterPress(Screen screen, Consumer<KeyEventWrapper> consumer) {
        //? if >=1.21.9 {
        /*ScreenKeyboardEvents.afterKeyPress(screen).register(((screen1, event) ->  {
            consumer.accept(KeyEventWrapper.create(event));
        }));
        *///?} else {
        ScreenKeyboardEvents.afterKeyPress(screen).register(((screen1, key, scanCode, modifiers) ->  {
            consumer.accept(KeyEventWrapper.create(key, scanCode, modifiers));
        }));
        //?}
    }

    public static void registerAfterRelease(Screen screen, Consumer<KeyEventWrapper> consumer) {
        //? if >=1.21.9 {
        /*ScreenKeyboardEvents.afterKeyRelease(screen).register(((screen1, event) ->  {
            consumer.accept(KeyEventWrapper.create(event));
        }));
        *///?} else {
        ScreenKeyboardEvents.afterKeyRelease(screen).register(((screen1, key, scanCode, modifiers) ->  {
            consumer.accept(KeyEventWrapper.create(key, scanCode, modifiers));
        }));
        //?}
    }
}

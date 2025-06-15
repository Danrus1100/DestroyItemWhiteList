package com.danrus.utils;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;

public class DrawUtils {
    public static void drawLocked(DrawContext context, int x, int y) {
        drawTexture(context, "locked", x, y);
    }

    public static void drawLockedItem(DrawContext context, int x, int y) {
        drawTexture(context, "locked_item", x, y);
    }

    public static void drawTexture(DrawContext context, String id, int x, int y) {

        //? if =1.20.1 {
        /*context.drawTexture(Identifier.of("diwl", "textures/gui/sprites/" + id + ".png"), x-1, y-1, 0, 0, 18, 18, 18, 18);
        *///?} elif <1.21.2 {
        context.drawGuiTexture(Identifier.of("diwl", id), x-1, y-1, 18, 18);
         //?} else {
        /*context.drawGuiTexture(RenderLayer::getGuiTextured, Identifier.of("diwl", id), x-1, y-1, 18, 18);
         *///?}
    }
}

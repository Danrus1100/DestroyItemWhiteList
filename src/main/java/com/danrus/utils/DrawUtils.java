package com.danrus.utils;

//? if >1.21.6 {
/*import net.minecraft.client.renderer.RenderPipelines;
*///?}

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class DrawUtils {
    public static void drawLocked(GuiGraphics context, int x, int y) {
        drawTexture(context, "locked", x, y);
    }

    public static void drawLockedItem(GuiGraphics context, int x, int y) {
        drawTexture(context, "locked_item", x, y);
    }

    public static void drawTexture(GuiGraphics context, String id, int x, int y) {

        //? if <1.21.2 {
        context.blitSprite(ResourceLocation.fromNamespaceAndPath("diwl", id), x-1, y-1, 18, 18);
        //?} elif <1.21.6 {
        /*context.blitSprite(RenderType::guiTextured, ResourceLocation.fromNamespaceAndPath("diwl", id), x-1, y-1, 18, 18);
        *///?} else {
        /*context.blitSprite(RenderPipelines.GUI_TEXTURED, ResourceLocation.fromNamespaceAndPath("diwl", id), x-1, y-1, 18, 18);
        *///?}
    }
}

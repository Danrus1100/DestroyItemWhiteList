package com.danrus.mixin;

import com.danrus.DestroyItemWhiteList;
import com.danrus.KeyBindsManager;
import com.danrus.SlotsUtils;
import com.danrus.config.ModConfig;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen.*;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HandledScreen.class)
public class HandledScreenMixin {

    @Unique
    private static final Identifier LOCKED_TEXTURE = Identifier.of("diwl", "locked");

    @Inject(
            method = "drawSlot",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/util/math/MatrixStack;pop()V")
    )
    private void drawSlotMixin(DrawContext context, Slot slot, CallbackInfo ci) {
        if (slot instanceof CreativeSlot) {
            CreativeSlot creativeSlot = (CreativeSlot) slot;
            if (!SlotsUtils.shouldBeDeleted(creativeSlot.slot.id) && (!ModConfig.get().hideWhileBindUp || (ModConfig.get().hideWhileBindUp && KeyBindsManager.isDoToggleWhiteListPressed))) {
                context.drawGuiTexture(RenderLayer::getGuiTextured, LOCKED_TEXTURE, creativeSlot.x-1, creativeSlot.y-1, 18, 18);
            }
        }
    }
}

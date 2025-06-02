package com.danrus.mixin;

import com.danrus.KeyBindsManager;
import com.danrus.SlotsUtils;
import com.danrus.config.ModConfig;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen.CreativeSlot;
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
    private static final Identifier LOCKED_TEXTURE = Identifier.of("diwl", "textures/gui/sprites/locked.png");

    @Inject(
            method = "drawSlot",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/util/math/MatrixStack;pop()V")
    )
    private void drawSlotMixin(DrawContext context, Slot slot, CallbackInfo ci) {
        if (slot instanceof CreativeSlot creativeSlot) {
            if (!SlotsUtils.shouldBeDeleted(creativeSlot.slot.id) && (!ModConfig.get().hideWhileBindUp || (ModConfig.get().hideWhileBindUp && KeyBindsManager.isDoToggleWhiteListPressed))) {
                //? if <1.21.2 {
                /*context.drawTexture(LOCKED_TEXTURE, creativeSlot.x-1, creativeSlot.y-1 , 0, 0, 18, 18, 18, 18);
                 *///?} else {
                context.drawTexture(RenderLayer::getGuiTextured, LOCKED_TEXTURE, creativeSlot.x-1, creativeSlot.y-1, 0, 0, 18, 18, 18, 18);
                //?}
            }
        }
    }
}
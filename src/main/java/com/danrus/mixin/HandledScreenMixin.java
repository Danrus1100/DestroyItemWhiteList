package com.danrus.mixin;

import com.danrus.KeyBindsManager;
import com.danrus.utils.DrawUtils;
import com.danrus.utils.WhiteListUtils;
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

    @Inject(
            method = "drawSlot",
//            at = @At(
//                    value = "INVOKE",
//                    target = "Lnet/minecraft/client/util/math/MatrixStack;pop()V")
            at = @At("TAIL")
    )
    private void drawSlotMixin(DrawContext context, Slot slot, CallbackInfo ci) {
        if (slot instanceof CreativeSlot creativeSlot) {
            if (!WhiteListUtils.isNotInSlots(creativeSlot.slot.id) && ((!ModConfig.get().hideWhiteListedSlots || (ModConfig.get().showWhenBindPressed && KeyBindsManager.isDoToggleWhiteListPressed)) || (!ModConfig.get().hideWhiteListedSlots || (ModConfig.get().showWhenDIHovered && WhiteListUtils.isDestroyItemSlotFocused())))) {
                DrawUtils.drawLocked(context, creativeSlot.x, creativeSlot.y);
            }
            if (!WhiteListUtils.isNotInItems(creativeSlot.slot.getStack().getItem()) && ((!ModConfig.get().hideWhiteListedItems || (ModConfig.get().showWhenBindPressed && KeyBindsManager.isDoToggleWhiteListPressed)) ||  (!ModConfig.get().hideWhiteListedItems || (ModConfig.get().showWhenDIHovered && WhiteListUtils.isDestroyItemSlotFocused())))) {
                DrawUtils.drawLockedItem(context, creativeSlot.x, creativeSlot.y);
            }
        }
    }
}
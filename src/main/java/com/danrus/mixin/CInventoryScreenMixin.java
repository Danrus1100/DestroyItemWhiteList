package com.danrus.mixin;

import com.danrus.DestroyItemWhiteList;
import com.danrus.KeyBindsManager;
import com.danrus.SlotsUtils;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;

@Mixin(CreativeInventoryScreen.class)
public class CInventoryScreenMixin {

    @Shadow @Nullable private List<Slot> slots;

    @WrapOperation(
            method = "onMouseClick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/screen/PlayerScreenHandler;onSlotClick(IILnet/minecraft/screen/slot/SlotActionType;Lnet/minecraft/entity/player/PlayerEntity;)V"
            )
    )
    private void onSlotClickMixin(PlayerScreenHandler instance, int slotId, int button, SlotActionType slotActionType, PlayerEntity player, Operation<Void> original) {
        if (KeyBindsManager.isDoToggleWhiteListPressed) {
            SlotsUtils.toggle(slotId);
        } else {
            original.call(instance, slotId, button, slotActionType, player);
        }
    }

    //? if >=1.21 {
    @WrapOperation(
            method = "onMouseClick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/screen/slot/Slot;setStackNoCallbacks(Lnet/minecraft/item/ItemStack;)V"
            )
    )
    private void onSlotClickMixin2(Slot instance, ItemStack stack, Operation<Void> original) {
        if (SlotsUtils.shouldBeDeleted(instance.id)) {
            original.call(instance, stack);
        }
    }
    //?}

    @WrapOperation(
            method = "onMouseClick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/network/ClientPlayerInteractionManager;clickCreativeStack(Lnet/minecraft/item/ItemStack;I)V"
            )

    )
    private void onSlotClickMixin3(ClientPlayerInteractionManager instance, ItemStack stack, int slotId, Operation<Void> original) {
        if (stack.isEmpty() && SlotsUtils.shouldBeDeleted(slotId)) {
            original.call(instance, stack, slotId);
        }
    }
}

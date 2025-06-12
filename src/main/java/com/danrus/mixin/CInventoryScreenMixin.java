package com.danrus.mixin;

import com.danrus.KeyBindsManager;
import com.danrus.slots.ForceDeleteManager;
import com.danrus.slots.SlotsUtils;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CreativeInventoryScreen.class)
public class CInventoryScreenMixin<T extends ScreenHandler> {

//    public Slot whiteListSlot;

    @Shadow private Slot deleteItemSlot;

//    @Shadow @Final private static SimpleInventory INVENTORY;

    @WrapOperation(
            method = "onMouseClick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/screen/PlayerScreenHandler;onSlotClick(IILnet/minecraft/screen/slot/SlotActionType;Lnet/minecraft/entity/player/PlayerEntity;)V"
            )
    )
    private void onSlotClickMixin(PlayerScreenHandler instance, int slotId, int button, SlotActionType slotActionType, PlayerEntity player, Operation<Void> original) {
        if (KeyBindsManager.isDoToggleWhiteListPressed) {
            if (button == 0) {SlotsUtils.toggle(slotId);}
            else if (button == 1) {
                Item item = instance.getSlot(slotId).getStack().getItem();
                if (item != Items.AIR) {
                    SlotsUtils.toggle(item);
                }
            }
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
        if (SlotsUtils.shouldBeDeleted(instance.getStack().getItem(), instance.id) || ForceDeleteManager.shouldBeForceDeleted()) {
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
        if (stack.isEmpty() && SlotsUtils.shouldBeDeleted(stack.getItem(),  slotId) || ForceDeleteManager.shouldBeForceDeleted()) {
            original.call(instance, stack, slotId);
        }
    }

    @Inject(
            method = "onMouseClick",
            at = @At("HEAD")
    )
    private void onShiftAndDIClicked(Slot slot, int slotId, int button, SlotActionType actionType, CallbackInfo ci){
        if (slot == this.deleteItemSlot && actionType == SlotActionType.QUICK_MOVE) {
            ForceDeleteManager.onClick();
        }
    }


    //TODO make fuking slot for stoopid ppl
//    @Inject(
//            method = "onMouseClick",
//            at = @At(
//                    value = "INVOKE",
//                    target = "Lnet/minecraft/screen/PlayerScreenHandler;onSlotClick(IILnet/minecraft/screen/slot/SlotActionType;Lnet/minecraft/entity/player/PlayerEntity;)V"
//            )
//    )
//    private void onCreativeSlotClick(Slot slot, int slotId, int button, SlotActionType actionType, CallbackInfo ci) {
//        if (slot instanceof CreativeInventoryScreen.CreativeSlot && ModConfig.get().behaviorMode == ModConfig.BEHAVIOR_MODE.ITEM) {
//            System.out.println("Button: " + button);
//            System.out.println("ActionType: " + actionType.toString());
//        }
//    }

//    @Inject(
//            method = "onMouseClick",
//            at = @At("HEAD"),
//            cancellable = true
//    )
//    private void onWhiteListSlot(Slot slot, int slotId, int button, SlotActionType actionType, CallbackInfo ci){
//        CreativeInventoryScreen thisObject = ((CreativeInventoryScreen)(Object)this);
//        boolean bl = actionType == SlotActionType.QUICK_MOVE;
//        actionType = slotId == -999 && actionType == SlotActionType.PICKUP ? SlotActionType.THROW : actionType;
//        if (actionType != SlotActionType.THROW || MinecraftClient.getInstance().player.canDropItems()) {
//            if (slot == null && actionType != SlotActionType.QUICK_CRAFT) {
//                if (!(!((CreativeInventoryScreen.CreativeScreenHandler)thisObject.handler).getCursorStack().isEmpty())) {
//
//                    if (slot == this.whiteListSlot && bl) {
//                        System.out.println("Hi");
//                        ci.cancel();
//                    }
//                }
//            }
//        }
//    }

//    @Inject(
//            method = "setSelectedTab",
//            at = @At(value = "INVOKE", target = "Lnet/minecraft/screen/slot/Slot;<init>(Lnet/minecraft/inventory/Inventory;III)V")
//    )
//    private void addWhiteListSlot(ItemGroup group, CallbackInfo ci) {
//        HandledScreen thisObject = ((HandledScreen)(Object)this);
//        this.whiteListSlot = new Slot(INVENTORY, -50, 174, 89);
//        ((CreativeInventoryScreen.CreativeScreenHandler)thisObject.handler).slots.add(this.deleteItemSlot);
//
//    }
}

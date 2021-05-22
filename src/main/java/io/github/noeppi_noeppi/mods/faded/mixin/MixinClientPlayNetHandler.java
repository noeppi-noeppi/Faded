package io.github.noeppi_noeppi.mods.faded.mixin;

import io.github.noeppi_noeppi.mods.faded.mods.usefultotems.ItemBetterTotem;
import net.minecraft.client.network.play.ClientPlayNetHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayNetHandler.class)
public class MixinClientPlayNetHandler {

    @Inject(
            method = "Lnet/minecraft/client/network/play/ClientPlayNetHandler;getTotemItem(Lnet/minecraft/entity/player/PlayerEntity;)Lnet/minecraft/item/ItemStack;",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void getTotemItem(PlayerEntity player, CallbackInfoReturnable<ItemStack> cir) {
        for (Hand hand : Hand.values()) {
            ItemStack stack = player.getHeldItem(hand);
            if (stack.getItem() instanceof ItemBetterTotem) {
                cir.setReturnValue(stack);
            }
        }
    }
}

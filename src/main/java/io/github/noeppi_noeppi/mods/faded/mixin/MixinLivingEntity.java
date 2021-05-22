package io.github.noeppi_noeppi.mods.faded.mixin;

import io.github.noeppi_noeppi.mods.faded.mods.usefultotems.ItemBetterTotem;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class MixinLivingEntity {
    
    @Inject(
            method = "Lnet/minecraft/entity/LivingEntity;checkTotemDeathProtection(Lnet/minecraft/util/DamageSource;)Z",
            at = @At("HEAD"),
            cancellable = true
    )
    public void checkTotemDeathProtection(DamageSource damage, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity living = (LivingEntity) (Object) this;
        World world = living.world;
        if (!world.isRemote) {
            for (Hand hand : Hand.values()) {
                ItemStack stack = living.getHeldItem(hand);
                if (stack.getItem() instanceof ItemBetterTotem) {
                    if (((ItemBetterTotem) stack.getItem()).applyEffect(world, stack, living, damage)) {
                        ItemStack copy = stack.copy();
                        stack.shrink(1);
                        //noinspection ConstantConditions
                        if (living instanceof ServerPlayerEntity) {
                            ServerPlayerEntity player = (ServerPlayerEntity) living;
                            player.addStat(Stats.ITEM_USED.get(copy.getItem()));
                            CriteriaTriggers.USED_TOTEM.trigger(player, copy);
                        }
                        world.setEntityState(living, (byte) 35);
                        cir.setReturnValue(true);
                        return;
                    }
                }
            }
        }
    }
}

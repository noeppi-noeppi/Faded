package io.github.noeppi_noeppi.mods.faded.mods.usefultotems;

import io.github.noeppi_noeppi.libx.mod.ModX;
import io.github.noeppi_noeppi.libx.mod.registration.ItemBase;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public abstract class ItemBetterTotem extends ItemBase {

    public ItemBetterTotem(ModX mod, Properties properties) {
        super(mod, properties.maxStackSize(1).group(UsefulTotems.TAB).rarity(Rarity.EPIC));
    }

    public abstract boolean applyEffect(World world, ItemStack stack, LivingEntity living, DamageSource damage);
    
    protected final void basicTotem(LivingEntity living, float health) {
        living.forceFireTicks(0);
        living.fallDistance = 0;
        living.clearActivePotions();
        living.addPotionEffect(new EffectInstance(Effects.ABSORPTION, 400, 2));
        living.addPotionEffect(new EffectInstance(Effects.REGENERATION, 400, 0));
        living.setHealth(health);
    }
}

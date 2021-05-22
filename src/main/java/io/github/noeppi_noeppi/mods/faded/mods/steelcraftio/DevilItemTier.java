package io.github.noeppi_noeppi.mods.faded.mods.steelcraftio;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemTier;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundEvent;

import javax.annotation.Nonnull;

public class DevilItemTier implements IItemTier, IArmorMaterial {

    @Override
    public int getMaxUses() {
        return ItemTier.NETHERITE.getMaxUses();
    }

    @Override
    public float getEfficiency() {
        return ItemTier.GOLD.getEfficiency();
    }

    @Override
    public float getAttackDamage() {
        return ItemTier.NETHERITE.getAttackDamage() + 1;
    }

    @Override
    public int getHarvestLevel() {
        return ItemTier.NETHERITE.getHarvestLevel();
    }

    @Override
    public int getEnchantability() {
        return ItemTier.GOLD.getEnchantability();
    }

    @Nonnull
    @Override
    public Ingredient getRepairMaterial() {
        return Ingredient.fromItems(SteelCraftIO.devil);
    }

    @Override
    public int getDurability(@Nonnull EquipmentSlotType slot) {
        return ArmorMaterial.NETHERITE.getDurability(slot);
    }

    @Override
    public int getDamageReductionAmount(@Nonnull EquipmentSlotType slot) {
        return ArmorMaterial.NETHERITE.getDamageReductionAmount(slot) + 1;
    }

    @Nonnull
    @Override
    public SoundEvent getSoundEvent() {
        return ArmorMaterial.NETHERITE.getSoundEvent();
    }

    @Nonnull
    @Override
    public String getName() {
        return "faded_steelcraftio_devil";
    }

    @Override
    public float getToughness() {
        return 5;
    }

    @Override
    public float getKnockbackResistance() {
        return ArmorMaterial.NETHERITE.getKnockbackResistance();
    }
}

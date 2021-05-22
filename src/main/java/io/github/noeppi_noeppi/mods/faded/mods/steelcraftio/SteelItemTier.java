package io.github.noeppi_noeppi.mods.faded.mods.steelcraftio;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemTier;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundEvent;

import javax.annotation.Nonnull;

public class SteelItemTier implements IItemTier, IArmorMaterial {

    @Override
    public int getMaxUses() {
        return ItemTier.IRON.getMaxUses() * 2;
    }

    @Override
    public float getEfficiency() {
        return ItemTier.IRON.getEfficiency() + 1;
    }

    @Override
    public float getAttackDamage() {
        return ItemTier.IRON.getAttackDamage() + 0.5f;
    }

    @Override
    public int getHarvestLevel() {
        return ItemTier.IRON.getHarvestLevel();
    }

    @Override
    public int getEnchantability() {
        return (ItemTier.IRON.getEnchantability() + ItemTier.GOLD.getEnchantability()) / 2;
    }

    @Nonnull
    @Override
    public Ingredient getRepairMaterial() {
        return Ingredient.fromItems(SteelCraftIO.steel);
    }

    @Override
    public int getDurability(@Nonnull EquipmentSlotType slot) {
        return ArmorMaterial.IRON.getDurability(slot) * 2;
    }

    @Override
    public int getDamageReductionAmount(@Nonnull EquipmentSlotType slot) {
        return ArmorMaterial.IRON.getDamageReductionAmount(slot) + (slot == EquipmentSlotType.CHEST ? 1 : 0);
    }

    @Nonnull
    @Override
    public SoundEvent getSoundEvent() {
        return ArmorMaterial.IRON.getSoundEvent();
    }

    @Nonnull
    @Override
    public String getName() {
        return "faded_steelcraftio_steel";
    }

    @Override
    public float getToughness() {
        return 1;
    }

    @Override
    public float getKnockbackResistance() {
        return 0;
    }
}

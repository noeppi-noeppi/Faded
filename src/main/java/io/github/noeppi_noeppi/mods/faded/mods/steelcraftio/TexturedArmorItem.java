package io.github.noeppi_noeppi.mods.faded.mods.steelcraftio;

import io.github.noeppi_noeppi.mods.faded.Faded;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;

public class TexturedArmorItem extends ArmorItem {

    private final String name;
    
    public TexturedArmorItem(IArmorMaterial materialIn, EquipmentSlotType slot, String name, Properties builderIn) {
        super(materialIn, slot, builderIn);
        this.name = name;
    }

    @Nullable
    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
        return Faded.getInstance().modid + ":textures/model/armor/steelcraftio_" + this.name + "_layer_" + (slot == EquipmentSlotType.LEGS ? "2" : "1") + ".png";
    }
}

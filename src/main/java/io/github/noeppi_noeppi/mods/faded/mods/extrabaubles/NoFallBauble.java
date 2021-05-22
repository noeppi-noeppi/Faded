package io.github.noeppi_noeppi.mods.faded.mods.extrabaubles;

import io.github.noeppi_noeppi.libx.mod.ModX;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import top.theillusivec4.curios.api.SlotTypePreset;

public class NoFallBauble extends ItemBauble {

    public NoFallBauble(ModX mod, SlotTypePreset slot) {
        this(mod, slot, new Item.Properties());
    }

    public NoFallBauble(ModX mod, SlotTypePreset slot, Properties properties) {
        super(mod, slot, properties.group(ExtraBaubles.TAB).maxStackSize(1));
    }

    @Override
    public void tick(ItemStack stack, LivingEntity entity) {
        entity.fallDistance = 0;
    }
}

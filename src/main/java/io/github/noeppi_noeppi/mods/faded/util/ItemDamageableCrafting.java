package io.github.noeppi_noeppi.mods.faded.util;

import io.github.noeppi_noeppi.libx.mod.ModX;
import io.github.noeppi_noeppi.libx.mod.registration.ItemBase;
import net.minecraft.item.ItemStack;

import java.util.Random;

public class ItemDamageableCrafting extends ItemBase {

    public ItemDamageableCrafting(ModX mod, Properties properties) {
        super(mod, properties);
    }

    @Override
    public boolean hasContainerItem(ItemStack stack) {
        return stack.getDamage() < stack.getMaxDamage();
    }

    @Override
    public ItemStack getContainerItem(ItemStack stack) {
        ItemStack copy = stack.copy();
        copy.attemptDamageItem(1, new Random(), null);
        return copy;
    }
}

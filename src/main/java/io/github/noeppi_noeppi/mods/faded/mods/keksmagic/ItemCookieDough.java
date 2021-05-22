package io.github.noeppi_noeppi.mods.faded.mods.keksmagic;

import io.github.noeppi_noeppi.libx.mod.ModX;
import io.github.noeppi_noeppi.libx.mod.registration.ItemBase;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class ItemCookieDough extends ItemBase {

    public final int tier;
    
    public ItemCookieDough(ModX mod, int tier, Properties properties) {
        super(mod, properties);
        this.tier = tier;
    }

    @Override
    public boolean hasContainerItem(ItemStack stack) {
        return true;
    }

    @Override
    public ItemStack getContainerItem(ItemStack stack) {
        return new ItemStack(Items.BOWL);
    }
}

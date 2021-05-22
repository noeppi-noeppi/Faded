package io.github.noeppi_noeppi.mods.faded.mods.usefultotems;

import io.github.noeppi_noeppi.libx.annotation.NoReg;
import io.github.noeppi_noeppi.libx.annotation.RegisterClass;
import io.github.noeppi_noeppi.libx.mod.registration.ItemBase;
import io.github.noeppi_noeppi.mods.faded.Faded;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

@RegisterClass(prefix = "useful_totems")
public class UsefulTotems {
    
    @NoReg
    public static final ItemGroup TAB = new ItemGroup(Faded.getInstance().modid + "_useful_totems") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(salvageTotem);
        }
    };
    
    public static final ItemBase teleportTotem = new ItemTeleportTotem(Faded.getInstance(), new Item.Properties());
    public static final ItemBase salvageTotem = new ItemSalvageTotem(Faded.getInstance(), new Item.Properties());
}

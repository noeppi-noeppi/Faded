package io.github.noeppi_noeppi.mods.faded.mods.random_wands;

import io.github.noeppi_noeppi.libx.annotation.NoReg;
import io.github.noeppi_noeppi.libx.annotation.RegisterClass;
import io.github.noeppi_noeppi.libx.mod.registration.ItemBase;
import io.github.noeppi_noeppi.mods.faded.Faded;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

@RegisterClass(prefix = "random_wands")
public class RandomWands {

    @NoReg
    public static final ItemGroup TAB = new ItemGroup(Faded.getInstance().modid + "_random_wands") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(pigWand);
        }
    };
    
    public static final ItemBase pigWand = new ItemMobPlace(Faded.getInstance(), EntityType.PIG, new Item.Properties().group(TAB).maxStackSize(1));
    public static final ItemBase cowWand = new ItemMobPlace(Faded.getInstance(), EntityType.COW, new Item.Properties().group(TAB).maxStackSize(1));
    public static final ItemBase chickenWand = new ItemMobPlace(Faded.getInstance(), EntityType.CHICKEN, new Item.Properties().group(TAB).maxStackSize(1));
    public static final ItemBase mooshroomWand = new ItemMobPlace(Faded.getInstance(), EntityType.MOOSHROOM, new Item.Properties().group(TAB).maxStackSize(1));
    public static final ItemBase squidWand = new ItemMobPlace(Faded.getInstance(), EntityType.SQUID, new Item.Properties().group(TAB).maxStackSize(1));
    public static final ItemBase iceWand = new ItemTrapWand(Faded.getInstance(), Blocks.ICE.getDefaultState(), new Item.Properties().group(TAB).maxStackSize(1));
    public static final ItemBase obsidianWand = new ItemTrapWand(Faded.getInstance(), Blocks.OBSIDIAN.getDefaultState(), new Item.Properties().group(TAB).maxStackSize(1));
}

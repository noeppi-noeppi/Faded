package io.github.noeppi_noeppi.mods.faded.mods.steelcraftio;

import io.github.noeppi_noeppi.libx.annotation.NoReg;
import io.github.noeppi_noeppi.libx.annotation.RegisterClass;
import io.github.noeppi_noeppi.libx.mod.registration.BlockBase;
import io.github.noeppi_noeppi.libx.mod.registration.ItemBase;
import io.github.noeppi_noeppi.mods.faded.Faded;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.material.Material;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.ToolType;

@RegisterClass(prefix = "steelcraftio")
public class SteelCraftIO {
    
    @NoReg
    public static final ItemGroup TAB = new ItemGroup(Faded.getInstance().modid + "_steelcraftio") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(steel);
        }
    };
    
    @NoReg public static final SteelItemTier STEEL = new SteelItemTier();
    @NoReg public static final DevilItemTier DEVIL = new DevilItemTier();
    
    public static final ItemBase rawSteel = new ItemBase(Faded.getInstance(), new Item.Properties().group(TAB));
    public static final ItemBase steel = new ItemBase(Faded.getInstance(), new Item.Properties().group(TAB));
    public static final ItemBase tinyDiamond = new ItemBase(Faded.getInstance(), new Item.Properties().group(TAB));
    public static final ItemBase coalNugget = new ItemBase(Faded.getInstance(), new Item.Properties().group(TAB)) {
        @Override
        public int getBurnTime(ItemStack itemStack) {
            return 200;
        }
    };
    public static final ItemBase devil = new ItemBase(Faded.getInstance(), new Item.Properties().group(TAB));
    public static final ItemBase devilDust = new ItemBase(Faded.getInstance(), new Item.Properties().group(TAB)) {
        @Override
        public int getBurnTime(ItemStack itemStack) {
            return 25600;
        }
    };
    public static final ItemBase bedrockNugget = new ItemBase(Faded.getInstance(), new Item.Properties().group(TAB));
    public static final ItemBase bedrockIngot = new ItemBase(Faded.getInstance(), new Item.Properties().group(TAB));
    
    public static final Item steelSword = new SwordItem(STEEL, 3, -2.4f, new Item.Properties().group(TAB));
    public static final Item steelAxe = new AxeItem(STEEL, 6, -3.1f, new Item.Properties().group(TAB));
    public static final Item steelPickaxe = new PickaxeItem(STEEL, 1, -2.8f, new Item.Properties().group(TAB));
    public static final Item steelShovel = new ShovelItem(STEEL, 1.5f, -3, new Item.Properties().group(TAB));
    public static final Item steelHoe = new HoeItem(STEEL, -2, -1, new Item.Properties().group(TAB));
    public static final Item steelHelmet = new TexturedArmorItem(STEEL, EquipmentSlotType.HEAD, "steel", new Item.Properties().group(TAB));
    public static final Item steelChestplate = new TexturedArmorItem(STEEL, EquipmentSlotType.CHEST, "steel", new Item.Properties().group(TAB));
    public static final Item steelLeggings = new TexturedArmorItem(STEEL, EquipmentSlotType.LEGS, "steel", new Item.Properties().group(TAB));
    public static final Item steelBoots = new TexturedArmorItem(STEEL, EquipmentSlotType.FEET, "steel", new Item.Properties().group(TAB));
    
    public static final Item devilSword = new SwordItem(DEVIL, 3, -2.4f, new Item.Properties().group(TAB));
    public static final Item devilAxe = new AxeItem(DEVIL, 5, -3, new Item.Properties().group(TAB));
    public static final Item devilPickaxe = new PickaxeItem(DEVIL, 1, -2.8f, new Item.Properties().group(TAB));
    public static final Item devilShovel = new ShovelItem(DEVIL, 1.5f, -3, new Item.Properties().group(TAB));
    public static final Item devilHoe = new HoeItem(DEVIL, -4, 0, new Item.Properties().group(TAB));
    public static final Item devilHelmet = new TexturedArmorItem(DEVIL, EquipmentSlotType.HEAD, "devil", new Item.Properties().group(TAB));
    public static final Item devilChestplate = new TexturedArmorItem(DEVIL, EquipmentSlotType.CHEST, "devil", new Item.Properties().group(TAB));
    public static final Item devilLeggings = new TexturedArmorItem(DEVIL, EquipmentSlotType.LEGS, "devil", new Item.Properties().group(TAB));
    public static final Item devilBoots = new TexturedArmorItem(DEVIL, EquipmentSlotType.FEET, "devil", new Item.Properties().group(TAB));
    
    public static final BlockBase steelBlock = new BlockBase(Faded.getInstance(), AbstractBlock.Properties.create(Material.IRON).hardnessAndResistance(6, 80).harvestTool(ToolType.PICKAXE).harvestLevel(2), new Item.Properties().group(TAB));
    public static final BlockBase devilBlock = new BlockBase(Faded.getInstance(), AbstractBlock.Properties.create(Material.IRON).hardnessAndResistance(25, 2000).setLightLevel(state -> 3).harvestTool(ToolType.PICKAXE).harvestLevel(3), new Item.Properties().group(TAB));
    public static final BlockBase devilOre = new BlockBase(Faded.getInstance(), AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(5, 30).setLightLevel(state -> 3).harvestTool(ToolType.PICKAXE).harvestLevel(3), new Item.Properties().group(TAB));
    public static final BlockBase denseOre = new BlockBase(Faded.getInstance(), AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(10, 30).harvestTool(ToolType.PICKAXE).harvestLevel(3), new Item.Properties().group(TAB));
    
    public static void init() {
        MinecraftForge.EVENT_BUS.addListener(SteelCraftIOWorldGen::loadBiome);
    }
}

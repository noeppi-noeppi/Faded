package io.github.noeppi_noeppi.mods.faded.mods.extrabaubles;

import io.github.noeppi_noeppi.libx.annotation.NoReg;
import io.github.noeppi_noeppi.libx.annotation.RegisterClass;
import io.github.noeppi_noeppi.libx.mod.registration.ItemBase;
import io.github.noeppi_noeppi.mods.faded.Faded;
import io.github.noeppi_noeppi.mods.faded.util.ItemDamageableCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.ItemLootEntry;
import net.minecraft.loot.LootEntry;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTables;
import net.minecraft.potion.Effects;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import top.theillusivec4.curios.api.SlotTypePreset;

import javax.annotation.Nullable;
import java.util.List;

@RegisterClass(prefix = "extrabaubles")
public class ExtraBaubles {

    @NoReg
    public static final ItemGroup TAB = new ItemGroup(Faded.getInstance().modid + "_extrabaubles") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(diamondRing);
        }
    };
    
    public static final ItemBase pearl = new ItemBase(Faded.getInstance(), new Item.Properties().group(TAB));
    public static final ItemBase antiPoisonCrystal = new ItemBase(Faded.getInstance(), new Item.Properties().group(TAB));
    public static final ItemBase cutLapis = new ItemBase(Faded.getInstance(), new Item.Properties().group(TAB));
    public static final ItemBase cutQuartz = new ItemBase(Faded.getInstance(), new Item.Properties().group(TAB));
    public static final ItemBase cutDiamond = new ItemBase(Faded.getInstance(), new Item.Properties().group(TAB));
    public static final ItemBase cutObsidian = new ItemBase(Faded.getInstance(), new Item.Properties().group(TAB));
    public static final ItemBase heart1 = new ItemBase(Faded.getInstance(), new Item.Properties().group(TAB).maxStackSize(10));
    public static final ItemBase heart2 = new ItemBase(Faded.getInstance(), new Item.Properties().group(TAB).maxStackSize(10));
    public static final ItemBase heart3 = new ItemBase(Faded.getInstance(), new Item.Properties().group(TAB).maxStackSize(10));
    public static final ItemBase heart4 = new ItemBase(Faded.getInstance(), new Item.Properties().group(TAB).maxStackSize(10));
    public static final ItemBase heart5 = new ItemBase(Faded.getInstance(), new Item.Properties().group(TAB).maxStackSize(10));
    public static final ItemBase heart6 = new ItemBase(Faded.getInstance(), new Item.Properties().group(TAB).maxStackSize(10));
    public static final ItemBase crystalCutter = new ItemDamageableCrafting(Faded.getInstance(), new Item.Properties().group(TAB).maxStackSize(1).maxDamage(8));
    public static final ItemBase ring = new ItemBauble(Faded.getInstance(), SlotTypePreset.RING);
    public static final ItemBase pearlRing = new EffectBauble(Faded.getInstance(), SlotTypePreset.RING, Effects.WATER_BREATHING);
    public static final ItemBase antiPoisonRing = new UneffectBauble(Faded.getInstance(), SlotTypePreset.RING, Effects.POISON, Effects.WITHER);
    public static final ItemBase lapisRing = new EffectBauble(Faded.getInstance(), SlotTypePreset.RING, Effects.NIGHT_VISION);
    public static final ItemBase quartzRing = new NoFallBauble(Faded.getInstance(), SlotTypePreset.RING);
    public static final ItemBase diamondRing = new EffectBauble(Faded.getInstance(), SlotTypePreset.RING, 1, Effects.STRENGTH);
    public static final ItemBase obsidianRing = new EffectBauble(Faded.getInstance(), SlotTypePreset.RING, 2, Effects.RESISTANCE, Effects.SLOWNESS);
    public static final ItemBase beltGemmal = new EffectBauble(Faded.getInstance(), SlotTypePreset.BELT, Effects.INVISIBILITY);
    public static final ItemBase amuletLife1 = new LifeAmulet(Faded.getInstance(), SlotTypePreset.CHARM, 20);
    public static final ItemBase amuletLife2 = new LifeAmulet(Faded.getInstance(), SlotTypePreset.CHARM, 40);
    public static final ItemBase amuletLife3 = new LifeAmulet(Faded.getInstance(), SlotTypePreset.CHARM, 60);
    public static final ItemBase amuletLife4 = new LifeAmulet(Faded.getInstance(), SlotTypePreset.CHARM, 80);
    public static final ItemBase amuletLife5 = new LifeAmulet(Faded.getInstance(), SlotTypePreset.CHARM, 100);
    public static final ItemBase amuletLife6 = new LifeAmulet(Faded.getInstance(), SlotTypePreset.CHARM, 120);
    
    public static void init() {
        MinecraftForge.EVENT_BUS.addListener(ExtraBaubles::lootTableLoad);
    }
    
    private static void lootTableLoad(LootTableLoadEvent event) {
        if (event.getName().equals(LootTables.GAMEPLAY_FISHING_TREASURE)) {
            @Nullable
            LootPool pool = event.getTable().getPool("main");
            //noinspection ConstantConditions
            if (pool != null) {
                addEntry(pool, ItemLootEntry.builder(pearl).build());
            }
        }
    }

    private static void addEntry(LootPool pool, LootEntry entry) {
        try {
            //noinspection unchecked
            List<LootEntry> lootEntries = (List<LootEntry>) ObfuscationReflectionHelper.findField(LootPool.class, "field_186453_a").get(pool);
            if (lootEntries.stream().noneMatch(e -> e == entry)) {
                lootEntries.add(entry);
            }
        } catch (ReflectiveOperationException e) {
            //
        }
    }
}

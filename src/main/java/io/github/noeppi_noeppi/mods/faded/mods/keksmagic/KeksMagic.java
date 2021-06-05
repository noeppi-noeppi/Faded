package io.github.noeppi_noeppi.mods.faded.mods.keksmagic;

import io.github.noeppi_noeppi.libx.annotation.NoReg;
import io.github.noeppi_noeppi.libx.annotation.RegisterClass;
import io.github.noeppi_noeppi.libx.inventory.container.ContainerBase;
import io.github.noeppi_noeppi.libx.mod.registration.BlockGUI;
import io.github.noeppi_noeppi.libx.mod.registration.ItemBase;
import io.github.noeppi_noeppi.mods.faded.Faded;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.material.Material;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.Effects;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@RegisterClass(prefix = "keksmagic")
public class KeksMagic {
    
    @NoReg
    public static final ItemGroup TAB = new ItemGroup(Faded.getInstance().modid + "_keksmagic") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(Items.COOKIE);
        }
    };
    
    public static final BlockGUI<CookieAssembler, ContainerCookieAssembler> cookieAssembler = new BlockCookieAssembler(Faded.getInstance(), CookieAssembler.class, ContainerBase.createContainerType(ContainerCookieAssembler::new), AbstractBlock.Properties.create(Material.IRON).hardnessAndResistance(5, 30), new Item.Properties().group(TAB));
    public static final ItemCookie cookie = new ItemCookie(Faded.getInstance(), new Item.Properties().group(TAB));

    public static final ItemBase formSelf = new ItemCookieCutter(Faded.getInstance(), ItemCookieCutter.Type.SELF, new Item.Properties().group(TAB));
    public static final ItemBase formProjectile = new ItemCookieCutter(Faded.getInstance(), ItemCookieCutter.Type.PROJECTILE, new Item.Properties().group(TAB));
    
    public static final ItemBase dough1 = new ItemCookieDough(Faded.getInstance(), 1, new Item.Properties().group(TAB));
    public static final ItemBase dough2 = new ItemCookieDough(Faded.getInstance(), 2, new Item.Properties().group(TAB));
    public static final ItemBase dough3 = new ItemCookieDough(Faded.getInstance(), 3, new Item.Properties().group(TAB));
    public static final ItemBase dough4 = new ItemCookieDough(Faded.getInstance(), 4, new Item.Properties().group(TAB));
    
    public static final ItemBase cocoaBlindness = new ItemMagicalCocoa(Faded.getInstance(), Effects.BLINDNESS, new Item.Properties().group(TAB));
    public static final ItemBase cocoaFireResistance = new ItemMagicalCocoa(Faded.getInstance(), Effects.FIRE_RESISTANCE, new Item.Properties().group(TAB));
    public static final ItemBase cocoaHaste = new ItemMagicalCocoa(Faded.getInstance(), Effects.HASTE, new Item.Properties().group(TAB));
    public static final ItemBase cocoaHealthBoost = new ItemMagicalCocoa(Faded.getInstance(), Effects.HEALTH_BOOST, new Item.Properties().group(TAB));
    public static final ItemBase cocoaHunger = new ItemMagicalCocoa(Faded.getInstance(), Effects.HUNGER, new Item.Properties().group(TAB));
    public static final ItemBase cocoaInstantDamage = new ItemMagicalCocoa(Faded.getInstance(), Effects.INSTANT_DAMAGE, new Item.Properties().group(TAB));
    public static final ItemBase cocoaInstantHeal = new ItemMagicalCocoa(Faded.getInstance(), Effects.INSTANT_HEALTH, new Item.Properties().group(TAB));
    public static final ItemBase cocoaInvisibility = new ItemMagicalCocoa(Faded.getInstance(), Effects.INVISIBILITY, new Item.Properties().group(TAB));
    public static final ItemBase cocoaJump = new ItemMagicalCocoa(Faded.getInstance(), Effects.JUMP_BOOST, new Item.Properties().group(TAB));
    public static final ItemBase cocoaMiningFatigue = new ItemMagicalCocoa(Faded.getInstance(), Effects.MINING_FATIGUE, new Item.Properties().group(TAB));
    public static final ItemBase cocoaNausea = new ItemMagicalCocoa(Faded.getInstance(), Effects.NAUSEA, new Item.Properties().group(TAB));
    public static final ItemBase cocoaNightVision = new ItemMagicalCocoa(Faded.getInstance(), Effects.NIGHT_VISION, new Item.Properties().group(TAB));
    public static final ItemBase cocoaPoison = new ItemMagicalCocoa(Faded.getInstance(), Effects.POISON, new Item.Properties().group(TAB));
    public static final ItemBase cocoaRegeneration = new ItemMagicalCocoa(Faded.getInstance(), Effects.REGENERATION, new Item.Properties().group(TAB));
    public static final ItemBase cocoaResistance = new ItemMagicalCocoa(Faded.getInstance(), Effects.RESISTANCE, new Item.Properties().group(TAB));
    public static final ItemBase cocoaSlowness = new ItemMagicalCocoa(Faded.getInstance(), Effects.SLOWNESS, new Item.Properties().group(TAB));
    public static final ItemBase cocoaSpeed = new ItemMagicalCocoa(Faded.getInstance(), Effects.SPEED, new Item.Properties().group(TAB));
    public static final ItemBase cocoaStrength = new ItemMagicalCocoa(Faded.getInstance(), Effects.STRENGTH, new Item.Properties().group(TAB));
    public static final ItemBase cocoaWaterBreathing = new ItemMagicalCocoa(Faded.getInstance(), Effects.WATER_BREATHING, new Item.Properties().group(TAB));
    public static final ItemBase cocoaWeakness = new ItemMagicalCocoa(Faded.getInstance(), Effects.WEAKNESS, new Item.Properties().group(TAB));
    public static final ItemBase cocoaWither = new ItemMagicalCocoa(Faded.getInstance(), Effects.WITHER, new Item.Properties().group(TAB));

    public static void init() {
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            FMLJavaModLoadingContext.get().getModEventBus().addListener(KeksMagic::itemColors);
        });
    }

    public static void setup() {
        
    }
    
    @OnlyIn(Dist.CLIENT)
    public static void itemColors(ColorHandlerEvent.Item event) {
        event.getItemColors().register(cookie, cookie);
    }

    @OnlyIn(Dist.CLIENT)
    public static void clientSetup() {
        ScreenManager.registerFactory(cookieAssembler.container, ScreenCookieAssembler::new);
    }
}

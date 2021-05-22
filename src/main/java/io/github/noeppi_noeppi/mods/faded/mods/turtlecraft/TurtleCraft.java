package io.github.noeppi_noeppi.mods.faded.mods.turtlecraft;

import io.github.noeppi_noeppi.libx.annotation.NoReg;
import io.github.noeppi_noeppi.libx.annotation.RegisterClass;
import io.github.noeppi_noeppi.libx.mod.registration.ItemBase;
import io.github.noeppi_noeppi.mods.faded.Faded;
import net.minecraft.block.SaplingBlock;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.Tags;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

@RegisterClass(prefix = "turtlecraft")
public class TurtleCraft {
    
    @NoReg
    public static final ItemGroup TAB = new ItemGroup(Faded.getInstance().modid + "_turtlecraft") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(turtleSpawnEgg);
        }
    };
    
    public static final EntityType<Turtle> turtle = EntityType.Builder.<Turtle>create(Turtle::new, EntityClassification.CREATURE).size(1f, 0.6f).trackingRange(10).build(Faded.getInstance().modid + "_turtlecraft_turtle");
    
    public static final Item turtleSpawnEgg = new SpawnEggItem(turtle, 0x08BC00, 0xFFFFFF, new Item.Properties().group(TAB));
    public static final ItemBase turtleShell = new ItemBase(Faded.getInstance(), new Item.Properties().group(TAB));
    public static final ItemBase turtleApple = new ItemBase(Faded.getInstance(), new Item.Properties().group(TAB).food(new Food.Builder().hunger(4).saturation(1.2F).setAlwaysEdible().effect(() -> new EffectInstance(Effects.RESISTANCE, 20 * 20, 1), 1).build()));
    public static final ItemBase turtleRod = new ItemBase(Faded.getInstance(), new Item.Properties().group(TAB)) {
        
        @Override
        public boolean onLeftClickEntity(ItemStack stack, PlayerEntity player, Entity entity) {
            if (entity instanceof LivingEntity) {
                ((LivingEntity) entity).addPotionEffect(new EffectInstance(Effects.SLOWNESS, 40, 1));
            }
            return false;
        }
    };
    
    public static void init() {
        MinecraftForge.EVENT_BUS.addListener(TurtleCraftWorldGen::loadBiome);
    }
    
    public static void setup() {
        GlobalEntityTypeAttributes.put(turtle, Turtle.defaultAttributes());
        EntitySpawnPlacementRegistry.register(turtle, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, Turtle::canSpawnAt);
    }
    
    @OnlyIn(Dist.CLIENT)
    public static void clientSetup() {
        RenderingRegistry.registerEntityRenderingHandler(turtle, TurtleRender::new);
    }
}

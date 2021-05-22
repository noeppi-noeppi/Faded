package io.github.noeppi_noeppi.mods.faded.mods.moretnt;

import io.github.noeppi_noeppi.libx.annotation.NoReg;
import io.github.noeppi_noeppi.libx.annotation.RegisterClass;
import io.github.noeppi_noeppi.libx.mod.registration.BlockBase;
import io.github.noeppi_noeppi.mods.faded.Faded;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DispenserBlock;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.world.ExplosionEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

import java.util.Iterator;

@RegisterClass(prefix = "moretnt")
public class MoreTNT {

    @NoReg
    public static final ItemGroup TAB = new ItemGroup(Faded.getInstance().modid + "_moretnt") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(weakTnt);
        }
    };
    
    @NoReg public static final BooleanProperty BOMB = BooleanProperty.create("bomb");
    @NoReg public static final IntegerProperty FUSE = IntegerProperty.create("fuse", 1, 10);
    @NoReg public static final IntegerProperty POWER = IntegerProperty.create("power", 1, 10);
    
    public static final EntityType<PrimedExplodable> primedExplodable = EntityType.Builder.<PrimedExplodable>create(PrimedExplodable::new, EntityClassification.MISC).immuneToFire().size(0.98F, 0.98F).trackingRange(20).updateInterval(10).build(Faded.getInstance().modid + "_moretnt_primed");
    
    public static final BlockBase weakTnt = new PoweredTNT(Faded.getInstance(), new Item.Properties().group(TAB), 1.5f);
    public static final BlockBase betterTnt = new BetterTNT(Faded.getInstance(), new Item.Properties().group(TAB));
    public static final BlockBase groundTnt = new PoweredTNT(Faded.getInstance(), new Item.Properties().group(TAB), 5);

    public static void init() {
        MinecraftForge.EVENT_BUS.addListener(EventPriority.LOWEST, MoreTNT::explosionDone);
    }
    
    public static void setup() {
        DispenserBlock.registerDispenseBehavior(weakTnt, ExplodableDispenseBehaviour.INSTANCE);
        DispenserBlock.registerDispenseBehavior(betterTnt, ExplodableDispenseBehaviour.INSTANCE);
        DispenserBlock.registerDispenseBehavior(groundTnt, ExplodableDispenseBehaviour.INSTANCE);
    }
    
    @OnlyIn(Dist.CLIENT)
    public static void clientSetup() {
        RenderingRegistry.registerEntityRenderingHandler(primedExplodable, ExplodableRender::new);
    }
    
    private static void explosionDone(ExplosionEvent.Detonate event) {
        // We need to manually trigger explosions here
        // to prevent item drops from tnt.
        Iterator<BlockPos> itr = event.getAffectedBlocks().iterator();
        while (itr.hasNext()) {
            BlockPos pos = itr.next();
            BlockState state = event.getWorld().getBlockState(pos);
            if (state.getBlock() instanceof BlockExplodable) {
                itr.remove();
                ((BlockExplodable) state.getBlock()).prime(state, event.getWorld(), pos, true, true, event.getExplosion().getExplosivePlacedBy());
            }
        }
        if (event.getExplosion().getExploder() instanceof PrimedExplodable) {
            BlockState explodable = ((PrimedExplodable) event.getExplosion().getExploder()).getExplodable();
            if (explodable != null && explodable.getBlock() == groundTnt) {
                event.getAffectedBlocks().removeIf(pos -> {
                    Block block = event.getWorld().getBlockState(pos).getBlock();
                    return !Tags.Blocks.STONE.contains(block) && !Tags.Blocks.DIRT.contains(block)
                            && !Tags.Blocks.SAND.contains(block) && !Tags.Blocks.GRAVEL.contains(block)
                            && !Tags.Blocks.SANDSTONE.contains(block) && !Tags.Blocks.NETHERRACK.contains(block)
                            && !BlockTags.SOUL_SPEED_BLOCKS.contains(block) && !BlockTags.LOGS.contains(block)
                            && !BlockTags.LEAVES.contains(block) && !BlockTags.NYLIUM.contains(block)
                            && block != Blocks.GRASS_BLOCK && block != Blocks.NETHER_WART_BLOCK
                            && block != Blocks.WARPED_WART_BLOCK;
                });
            }
        }
    }
}

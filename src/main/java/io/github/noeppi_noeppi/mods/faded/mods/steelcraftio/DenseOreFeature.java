package io.github.noeppi_noeppi.mods.faded.mods.steelcraftio;

import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import javax.annotation.Nonnull;
import java.util.Random;

public class DenseOreFeature extends Feature<NoFeatureConfig> {
    
    public DenseOreFeature() {
        super(NoFeatureConfig.CODEC);
    }

    @Override
    public boolean generate(@Nonnull ISeedReader world, @Nonnull ChunkGenerator generator, @Nonnull Random rand, @Nonnull BlockPos basePos, @Nonnull NoFeatureConfig config) {
        boolean success = false;
        int attempts = 2 + rand.nextInt(2);
        for (int i = 0; i < attempts; i++) {
            BlockPos pos = new BlockPos(basePos.getX() + rand.nextInt(16), 2 + rand.nextInt(3), basePos.getZ() + rand.nextInt(16));
            if (world.getBlockState(pos).getBlock() == Blocks.BEDROCK) {
                world.setBlockState(pos, SteelCraftIO.denseOre.getDefaultState(), 2);
                success = true;
            }
        }
        return success;
    }
}

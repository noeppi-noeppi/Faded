package io.github.noeppi_noeppi.mods.faded.mods.steelcraftio;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraftforge.event.world.BiomeLoadingEvent;

public class SteelCraftIOWorldGen {

    public static ConfiguredFeature<?, ?> DEVIL_ORE = Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, SteelCraftIO.devilOre.getDefaultState(), 3)).range(10);
    public static ConfiguredFeature<?, ?> DENSE_ORE = new DenseOreFeature().withConfiguration(NoFeatureConfig.NO_FEATURE_CONFIG);

    public static void loadBiome(BiomeLoadingEvent event) {
        if (event.getCategory() != Biome.Category.NETHER && event.getCategory() != Biome.Category.THEEND) {
            event.getGeneration().withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, DEVIL_ORE);
        }
        event.getGeneration().withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, DENSE_ORE);
    }
}

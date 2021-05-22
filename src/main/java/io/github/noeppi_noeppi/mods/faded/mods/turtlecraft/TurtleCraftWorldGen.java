package io.github.noeppi_noeppi.mods.faded.mods.turtlecraft;

import net.minecraft.entity.EntityClassification;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraftforge.event.world.BiomeLoadingEvent;

public class TurtleCraftWorldGen {
    
    public static void loadBiome(BiomeLoadingEvent event) {
        if (event.getCategory() == Biome.Category.BEACH || event.getCategory() == Biome.Category.RIVER
                || event.getCategory() == Biome.Category.OCEAN) {
            event.getSpawns().withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(TurtleCraft.turtle, 50, 2, 5));
        }
    }
}

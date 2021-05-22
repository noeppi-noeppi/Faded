package io.github.noeppi_noeppi.mods.faded.data;

import io.github.noeppi_noeppi.mods.faded.Faded;
import io.github.noeppi_noeppi.mods.faded.data.recipes.RecipeProvider;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

public class DataGenerators {
	
    public static void gatherData(GatherDataEvent evt) {
		if (evt.includeServer()) {
			evt.getGenerator().addProvider(new BlockLootProvider(Faded.getInstance(), evt.getGenerator()));
			BlockTagProvider blockTagProvider = new BlockTagProvider(Faded.getInstance(), evt.getGenerator(), evt.getExistingFileHelper());
			evt.getGenerator().addProvider(blockTagProvider);
			evt.getGenerator().addProvider(new ItemTagProvider(Faded.getInstance(), evt.getGenerator(), evt.getExistingFileHelper(), blockTagProvider));
			evt.getGenerator().addProvider(new RecipeProvider(Faded.getInstance(), evt.getGenerator()));
		}
		if (evt.includeClient()) {
			evt.getGenerator().addProvider(new BlockStateProvider(Faded.getInstance(), evt.getGenerator(), evt.getExistingFileHelper()));
			evt.getGenerator().addProvider(new ItemModelProvider(Faded.getInstance(), evt.getGenerator(), evt.getExistingFileHelper()));
		}
	}
}

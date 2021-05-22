package io.github.noeppi_noeppi.mods.faded.data;

import io.github.noeppi_noeppi.libx.data.provider.ItemModelProviderBase;
import io.github.noeppi_noeppi.libx.mod.ModX;
import io.github.noeppi_noeppi.mods.faded.mods.keksmagic.KeksMagic;
import io.github.noeppi_noeppi.mods.faded.mods.steelcraftio.SteelCraftIO;
import io.github.noeppi_noeppi.mods.faded.mods.turtlecraft.TurtleCraft;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ItemModelProvider extends ItemModelProviderBase {

	public ItemModelProvider(ModX mod, DataGenerator generator, ExistingFileHelper fileHelper) {
		super(mod, generator, fileHelper);
	}

	@Override
	protected void setup() {
		this.handheld(SteelCraftIO.steelSword);
		this.handheld(SteelCraftIO.steelAxe);
		this.handheld(SteelCraftIO.steelPickaxe);
		this.handheld(SteelCraftIO.steelShovel);
		this.handheld(SteelCraftIO.steelHoe);
		this.handheld(SteelCraftIO.devilSword);
		this.handheld(SteelCraftIO.devilAxe);
		this.handheld(SteelCraftIO.devilPickaxe);
		this.handheld(SteelCraftIO.devilShovel);
		this.handheld(SteelCraftIO.devilHoe);
		this.handheld(TurtleCraft.turtleRod);
		this.manualModel(KeksMagic.cookie);
	}
}

package io.github.noeppi_noeppi.mods.faded.data;

import io.github.noeppi_noeppi.libx.data.provider.BlockLootProviderBase;
import io.github.noeppi_noeppi.libx.mod.ModX;
import io.github.noeppi_noeppi.mods.faded.mods.moretnt.BlockExplodable;
import io.github.noeppi_noeppi.mods.faded.mods.steelcraftio.SteelCraftIO;
import net.minecraft.advancements.criterion.EnchantmentPredicate;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.advancements.criterion.StatePropertiesPredicate;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.*;
import net.minecraft.loot.conditions.BlockStateProperty;
import net.minecraft.loot.conditions.MatchTool;
import net.minecraft.loot.conditions.SurvivesExplosion;
import net.minecraft.state.Property;
import net.minecraft.state.properties.BlockStateProperties;

import javax.annotation.Nullable;

public class BlockLootProvider extends BlockLootProviderBase {

    public BlockLootProvider(ModX mod, DataGenerator generator) {
        super(mod, generator);
    }

    @Override
    protected void setup() {
        this.drops(SteelCraftIO.devilOre, true, this.stack(SteelCraftIO.devilDust).with(this.fortuneOres()));
        this.drops(SteelCraftIO.denseOre, false,
                this.repeat(this.stack(new ItemStack(Blocks.COBBLESTONE, 64)), 27),
                this.stack(SteelCraftIO.bedrockNugget)
                        .with(this.fortuneUniform(2))
                        .with(MatchTool.builder(ItemPredicate.Builder.create().enchantment(
                                new EnchantmentPredicate(Enchantments.FORTUNE, MinMaxBounds.IntBound.atLeast(1))
                        )))
        );
    }

    @Nullable
    @Override
    protected LootTable.Builder defaultBehavior(Block b) {
        if (b instanceof BlockExplodable) {
            if (b.getStateContainer().getValidStates().stream().anyMatch(this::needsLootTable)) {
                LootEntry.Builder<?> entry = this.item().build(b);
                LootPool.Builder pool = LootPool.builder().name("main").rolls(ConstantRange.of(1)).addEntry(entry).acceptCondition(SurvivesExplosion.builder());
                return LootTable.builder().addLootPool(pool);
            } else {
                return null;
            }
        } else {
            return super.defaultBehavior(b);
        }
    }
}
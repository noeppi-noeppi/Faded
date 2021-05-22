package io.github.noeppi_noeppi.mods.faded.data;

import io.github.noeppi_noeppi.libx.data.provider.BlockStateProviderBase;
import io.github.noeppi_noeppi.libx.mod.ModX;
import io.github.noeppi_noeppi.mods.faded.Faded;
import io.github.noeppi_noeppi.mods.faded.mods.keksmagic.KeksMagic;
import io.github.noeppi_noeppi.mods.faded.mods.moretnt.BlockExplodable;
import io.github.noeppi_noeppi.mods.faded.mods.moretnt.MoreTNT;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.data.DataGenerator;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.VariantBlockStateBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

public class BlockStateProvider extends BlockStateProviderBase {

    public static final ResourceLocation TNT_TOP = new ResourceLocation("minecraft", "block/tnt_top");
    public static final ResourceLocation TNT_TOP_U = new ResourceLocation(Faded.getInstance().modid, "block/moretnt_tnt_top_unstable");
    public static final ResourceLocation TNT_TOP_B = new ResourceLocation(Faded.getInstance().modid, "block/moretnt_tnt_top_bomb");
    public static final ResourceLocation TNT_TOP_UB = new ResourceLocation(Faded.getInstance().modid, "block/moretnt_tnt_top_unstable_bomb");
    public static final ResourceLocation TNT_BOTTOM = new ResourceLocation("minecraft", "block/tnt_bottom");

    private final Map<Block, Function<BlockState, Pair<String, ResourceLocation>>> explodables = new HashMap<>();

    public BlockStateProvider(ModX mod, DataGenerator generator, ExistingFileHelper fileHelper) {
        super(mod, generator, fileHelper);
    }

    @Override
    protected void setup() {
        this.explodables.put(MoreTNT.betterTnt, state -> Pair.of(state.get(MoreTNT.POWER) == 1 ? "" : Integer.toString(state.get(MoreTNT.POWER)), new ResourceLocation(Faded.getInstance().modid, "block/moretnt_better_tnt_" + state.get(MoreTNT.POWER))));
        this.manualModel(KeksMagic.cookieAssembler);
    }

    @Override
    protected void defaultState(ResourceLocation id, Block block, ModelFile model) {
        if (block instanceof BlockExplodable) {
            Function<BlockState, Pair<String, ResourceLocation>> textureGetter;
            if (this.explodables.containsKey(block)) {
                textureGetter = this.explodables.get(block);
            } else {
                textureGetter = s -> Pair.of("", this.blockTexture(block));
            }
            VariantBlockStateBuilder builder = this.getVariantBuilder(block);
            builder.forAllStates(state -> {
                Pair<String, ResourceLocation> data = textureGetter.apply(state);
                String suffix = this.getModelSuffix(state, data.getLeft());
                return new ConfiguredModel[]{
                        new ConfiguredModel(this.models().cubeBottomTop(
                                Objects.requireNonNull(block.getRegistryName()).getPath() + suffix,
                                data.getRight(), TNT_BOTTOM, this.getTopTexture(state)
                        ), 0, 0, false)
                };
            });
        } else {
            super.defaultState(id, block, model);
        }
    }

    @Override
    protected ModelFile defaultModel(ResourceLocation id, Block block) {
        if (block instanceof BlockExplodable) {
            // This uses multiple models so we have no model here.
            return null;
        } else {
            return super.defaultModel(id, block);
        }
    }

    private String getModelSuffix(BlockState state, String custom) {
        StringBuilder sb = new StringBuilder();
        if (state.get(BlockStateProperties.UNSTABLE)) {
            sb.append("_unstable");
        }
        if (state.get(MoreTNT.BOMB)) {
            sb.append("_bomb");
        }
        if (!custom.isEmpty()) {
            sb.append("_").append(custom);
        }
        return sb.toString();
    }

    private ResourceLocation getTopTexture(BlockState state) {
        boolean unstable = state.get(BlockStateProperties.UNSTABLE);
        boolean bomb = state.get(MoreTNT.BOMB);
        if (unstable) {
            if (bomb) {
                return TNT_TOP_UB;
            } else {
                return TNT_TOP_U;
            }
        } else {
            if (bomb) {
                return TNT_TOP_B;
            } else {
                return TNT_TOP;
            }
        }
    }
}
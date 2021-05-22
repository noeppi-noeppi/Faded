package io.github.noeppi_noeppi.mods.faded.mods.moretnt;

import io.github.noeppi_noeppi.libx.mod.ModX;
import io.github.noeppi_noeppi.mods.faded.mods.moretnt.BlockExplodable;
import io.github.noeppi_noeppi.mods.faded.mods.moretnt.ExplodableProperties;
import io.github.noeppi_noeppi.mods.faded.mods.moretnt.MoreTNT;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class BetterTNT extends BlockExplodable {
    
    public BetterTNT(ModX mod) {
        super(mod);
    }

    public BetterTNT(ModX mod, Item.Properties itemProperties) {
        super(mod, itemProperties);
    }

    public BetterTNT(ModX mod, Properties properties) {
        super(mod, properties);
    }

    public BetterTNT(ModX mod, Properties properties, Item.Properties itemProperties) {
        super(mod, properties, itemProperties);
        this.setDefaultState(this.getDefaultState().with(MoreTNT.POWER, 1).with(MoreTNT.FUSE, 1));
    }

    @Override
    protected void fillStateContainer(@Nonnull StateContainer.Builder<Block, BlockState> builder) {
        super.fillStateContainer(builder);
        builder.add(MoreTNT.POWER);
        builder.add(MoreTNT.FUSE);
    }

    @Nonnull
    @Override
    public ActionResultType onBlockActivated(@Nonnull BlockState state, @Nonnull World world, @Nonnull BlockPos pos, PlayerEntity player, @Nonnull Hand hand, @Nonnull BlockRayTraceResult hit) {
        ItemStack stack = player.getHeldItem(hand);
        Item item = stack.getItem();
        if (item == Items.GUNPOWDER && state.get(MoreTNT.POWER) < 10) {
            world.setBlockState(pos, state.with(MoreTNT.POWER, state.get(MoreTNT.POWER) + 1), 3);
            if (!player.isCreative()) {
                stack.shrink(1);
            }
            return ActionResultType.successOrConsume(world.isRemote);
        } else if (item == Items.STRING && state.get(MoreTNT.FUSE) < 10) {
            world.setBlockState(pos, state.with(MoreTNT.FUSE, state.get(MoreTNT.FUSE) + 1), 3);
            if (!player.isCreative()) {
                stack.shrink(1);
            }
            return ActionResultType.successOrConsume(world.isRemote);
        } else {
            return super.onBlockActivated(state, world, pos, player, hand, hit);
        }
    }

    @Override
    public ExplodableProperties getExplosion(BlockState state, World world, BlockPos pos) {
        return new ExplodableProperties(2.5f + (1.5f * state.get(MoreTNT.POWER)), 20 * state.get(MoreTNT.FUSE));
    }
}

package io.github.noeppi_noeppi.mods.faded.mods.moretnt;

import io.github.noeppi_noeppi.libx.mod.ModX;
import io.github.noeppi_noeppi.libx.mod.registration.BlockBase;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class BlockExplodable extends BlockBase {

    public BlockExplodable(ModX mod) {
        this(mod, new Item.Properties());
    }
    
    public BlockExplodable(ModX mod, Item.Properties itemProperties) {
        this(mod, AbstractBlock.Properties.create(Material.TNT).zeroHardnessAndResistance().sound(SoundType.PLANT), itemProperties);
    }
    
    public BlockExplodable(ModX mod, Properties properties) {
        this(mod, properties, new Item.Properties());
    }

    public BlockExplodable(ModX mod, Properties properties, Item.Properties itemProperties) {
        super(mod, properties, itemProperties);
        this.setDefaultState(this.getStateContainer().getBaseState()
                .with(BlockStateProperties.UNSTABLE, false)
                .with(MoreTNT.BOMB, false)
        );
    }

    @Override
    protected void fillStateContainer(@Nonnull StateContainer.Builder<Block, BlockState> builder) {
        super.fillStateContainer(builder);
        builder.add(BlockStateProperties.UNSTABLE);
        builder.add(MoreTNT.BOMB);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onReplaced(@Nonnull BlockState state, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock() && !isMoving && state.get(BlockStateProperties.UNSTABLE)) {
            this.prime(state, world, pos, false, false, null);
        }
        super.onReplaced(state, world, pos, newState, isMoving);
    }

    @Override
    public void onBlockExploded(BlockState state, World world, BlockPos pos, Explosion explosion) {
        super.onBlockExploded(state, world, pos, explosion);
        this.prime(state, world, pos, true, false, explosion.getExplosivePlacedBy());
    }

    @Override
    @Nonnull
    @SuppressWarnings("deprecation")
    public ActionResultType onBlockActivated(@Nonnull BlockState state, @Nonnull World world, @Nonnull BlockPos pos, PlayerEntity player, @Nonnull Hand hand, @Nonnull BlockRayTraceResult hit) {
        ItemStack stack = player.getHeldItem(hand);
        Item item = stack.getItem();
        if (item == Items.FLINT && !state.get(BlockStateProperties.UNSTABLE)) {
            world.setBlockState(pos, state.with(BlockStateProperties.UNSTABLE, true), 3);
            if (!player.isCreative()) {
                stack.shrink(1);
            }
            return ActionResultType.successOrConsume(world.isRemote);
        } else if (item == Items.REDSTONE && !state.get(MoreTNT.BOMB)) {
            world.setBlockState(pos, state.with(MoreTNT.BOMB, true), 3);
            if (!player.isCreative()) {
                stack.shrink(1);
            }
            return ActionResultType.successOrConsume(world.isRemote);
        } else if (item == Items.FLINT_AND_STEEL) {
            this.prime(state, world, pos, true, false, player);
            if (!player.isCreative()) {
                stack.damageItem(1, player, p -> p.sendBreakAnimation(hand));
            }
            return ActionResultType.successOrConsume(world.isRemote);
        } else if (item == Items.FIRE_CHARGE) {
            this.prime(state, world, pos, true, false, player);
            if (!player.isCreative()) {
                stack.shrink(1);
            }
            return ActionResultType.successOrConsume(world.isRemote);
        } else {
            return super.onBlockActivated(state, world, pos, player, hand, hit);
        }
    }
    
    @Override
    public void catchFire(BlockState state, World world, BlockPos pos, @Nullable net.minecraft.util.Direction face, @Nullable LivingEntity igniter) {
        this.prime(state, world, pos, false, false, igniter);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void neighborChanged(@Nonnull BlockState state, World world, @Nonnull BlockPos pos, @Nonnull Block block, @Nonnull BlockPos fromPos, boolean isMoving) {
        if (world.isBlockPowered(pos)) {
            this.prime(state, world, pos, true, false, null);
        }
    }

    @Override
    public int getFlammability(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
        return 100;
    }

    public void prime(BlockState state, World world, BlockPos pos, boolean clearBlock, boolean shortFuse, @Nullable LivingEntity igniter) {
        if (!world.isRemote) {
            boolean bomb = state.get(MoreTNT.BOMB);
            ExplodableProperties properties = this.getExplosion(state, world, pos);
            if (clearBlock) {
                world.setBlockState(pos, state.getFluidState().getBlockState());
            }
            PrimedExplodable tnt = new PrimedExplodable(world, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, properties, shortFuse, bomb, state, igniter);
            world.addEntity(tnt);
            world.playSound(null, tnt.getPosX(), tnt.getPosY(), tnt.getPosZ(), SoundEvents.ENTITY_TNT_PRIMED, SoundCategory.BLOCKS, 1, 1);
        }
    }

    public abstract ExplodableProperties getExplosion(BlockState state, World world, BlockPos pos);

    public boolean doExplode(PrimedExplodable tnt, float power) {
        return false;
    }
}

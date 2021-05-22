package io.github.noeppi_noeppi.mods.faded.mods.random_wands;

import io.github.noeppi_noeppi.libx.mod.ModX;
import io.github.noeppi_noeppi.libx.mod.registration.ItemBase;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nonnull;

public class ItemTrapWand extends ItemBase {
    
    private final BlockState state;
    
    public ItemTrapWand(ModX mod, BlockState state, Properties properties) {
        super(mod, properties);
        this.state = state;
    }

    @Nonnull
    @Override
    public ActionResultType onItemUse(@Nonnull ItemUseContext ctx) {
        if (!ctx.getWorld().isRemote) {
            BlockPos pos = ctx.getPos().offset(ctx.getFace());
            BlockState state = ctx.getWorld().getBlockState(pos);
            //noinspection deprecation
            if (state.isAir() || state.isReplaceable(new BlockItemUseContext(ctx))) {
                ctx.getWorld().setBlockState(pos, this.state, 3);
            }
        }
        return ActionResultType.successOrConsume(ctx.getWorld().isRemote);
    }
    
    @Nonnull
    @Override
    public ActionResultType itemInteractionForEntity(@Nonnull ItemStack stack, @Nonnull PlayerEntity player, @Nonnull LivingEntity target, @Nonnull Hand hand) {
        if (!player.world.isRemote) {
            int height = 1 + (int) Math.ceil(target.getBoundingBox().getYSize());
            int width = (int) Math.ceil(Math.max(target.getBoundingBox().getXSize(), target.getBoundingBox().getZSize()) / 2d);
            BlockPos base = target.getPosition().down();
            for (int y = 0; y <= height; y++) {
                for (int x = -width; x <= width; x++) {
                    for (int z = -width; z <= width; z++) {
                        if (y == 0 || y == height || x == -width || x == width || z == -width || z == width) {
                            BlockPos pos = base.add(x, y, z);
                            BlockState old = player.world.getBlockState(pos);
                            float hardnessOld = old.getBlockHardness(player.world, pos);
                            float hardnessNew = this.state.getBlockHardness(player.world, pos);
                            if (hardnessOld >= 0 && hardnessOld < hardnessNew) {
                                player.world.setBlockState(pos, this.state, 3);
                            }
                        }
                    }
                }
            }
            target.setPositionAndUpdate(base.getX() + 0.5, base.getY(), base.getZ() + 0.5);
        }
        return ActionResultType.successOrConsume(player.world.isRemote);
    }
}

package io.github.noeppi_noeppi.mods.faded.mods.moretnt;

import net.minecraft.block.Block;
import net.minecraft.block.DispenserBlock;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IDispenseItemBehavior;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class ExplodableDispenseBehaviour implements IDispenseItemBehavior {

    public static ExplodableDispenseBehaviour INSTANCE = new ExplodableDispenseBehaviour();
    
    private ExplodableDispenseBehaviour() {
        
    }
    
    @Nonnull
    @Override
    public ItemStack dispense(@Nonnull IBlockSource source, @Nonnull ItemStack stack) {
        World world = source.getWorld();
        BlockPos pos = source.getBlockPos().offset(source.getBlockState().get(DispenserBlock.FACING));
        if (stack.getItem() instanceof BlockItem) {
            Block block = ((BlockItem) stack.getItem()).getBlock();
            if (block instanceof BlockExplodable) {
                ((BlockExplodable) block).prime(block.getDefaultState(), world, pos, false, false, null);
            }
        }
        stack.shrink(1);
        return stack;
    }
}

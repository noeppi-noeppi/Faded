package io.github.noeppi_noeppi.mods.faded.mods.moretnt;

import io.github.noeppi_noeppi.libx.mod.ModX;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PoweredTNT extends BlockExplodable {
    
    private final float power;
    
    public PoweredTNT(ModX mod, float power) {
        super(mod);
        this.power = power;
    }

    public PoweredTNT(ModX mod, Item.Properties itemProperties, float power) {
        super(mod, itemProperties);
        this.power = power;
    }

    public PoweredTNT(ModX mod, Properties properties, float power) {
        super(mod, properties);
        this.power = power;
    }

    public PoweredTNT(ModX mod, Properties properties, Item.Properties itemProperties, float power) {
        super(mod, properties, itemProperties);
        this.power = power;
    }

    @Override
    public ExplodableProperties getExplosion(BlockState state, World world, BlockPos pos) {
        return new ExplodableProperties(this.power);
    }
}

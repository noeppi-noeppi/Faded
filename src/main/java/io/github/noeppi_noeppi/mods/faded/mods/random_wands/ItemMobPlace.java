package io.github.noeppi_noeppi.mods.faded.mods.random_wands;

import io.github.noeppi_noeppi.libx.mod.ModX;
import io.github.noeppi_noeppi.libx.mod.registration.ItemBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nonnull;

public class ItemMobPlace extends ItemBase {

    private final EntityType<?> mob;

    public ItemMobPlace(ModX mod, EntityType<?> mob, Properties properties) {
        super(mod, properties);
        this.mob = mob;
    }

    @Nonnull
    @Override
    public ActionResultType onItemUse(@Nonnull ItemUseContext ctx) {
        if (!ctx.getWorld().isRemote) {
            BlockPos pos = ctx.getPos().offset(ctx.getFace());
            Entity entity = this.mob.create(ctx.getWorld());
            if (entity != null) {
                entity.setLocationAndAngles(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, (ctx.getWorld().rand.nextFloat() - 0.5f) * 360f, 0);
                ctx.getWorld().addEntity(entity);
            }
        }
        return ActionResultType.successOrConsume(ctx.getWorld().isRemote);
    }
}

package io.github.noeppi_noeppi.mods.faded.mods.usefultotems;

import io.github.noeppi_noeppi.libx.mod.ModX;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class ItemTeleportTotem extends ItemBetterTotem {

    public ItemTeleportTotem(ModX mod, Properties properties) {
        super(mod, properties);
    }

    @Override
    public boolean applyEffect(World world, ItemStack stack, LivingEntity living, DamageSource damage) {
        this.basicTotem(living, 2f);
        if (living instanceof ServerPlayerEntity) {
            ServerPlayerEntity player = (ServerPlayerEntity) living;
            ServerWorld target = player.getServerWorld().getServer().getWorld(((ServerPlayerEntity) living).getSpawnPointWorld());
            if (target == null) target = player.getServerWorld().getServer().getOverworld();
            BlockPos pos = player.getSpawnPointPos();
            if (pos == null) pos = player.getServerWorld().getServer().getOverworld().getSpawnPoint();
            player.teleport(target, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, player.getSpawnPointYaw(), 0);
        }
        return true;
    }
}

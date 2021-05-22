package io.github.noeppi_noeppi.mods.faded.mods.keksmagic;

import io.github.noeppi_noeppi.libx.inventory.container.ContainerBase;
import io.github.noeppi_noeppi.libx.inventory.slot.SlotOutputOnly;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nullable;

public class ContainerCookieAssembler extends ContainerBase<CookieAssembler> {

    protected ContainerCookieAssembler(@Nullable ContainerType<?> type, int windowId, World world, BlockPos pos, PlayerInventory playerInventory, PlayerEntity player) {
        super(type, windowId, world, pos, playerInventory, player, 8, 9);

        this.addSlot(new SlotItemHandler(this.tile.getContainerInventory(), 0, 16, 30));
        this.addSlot(new SlotItemHandler(this.tile.getContainerInventory(), 1, 39, 16));
        this.addSlot(new SlotItemHandler(this.tile.getContainerInventory(), 2, 63, 14));
        this.addSlot(new SlotItemHandler(this.tile.getContainerInventory(), 3, 87, 16));
        this.addSlot(new SlotItemHandler(this.tile.getContainerInventory(), 4, 110, 30));
        
        this.addSlot(new SlotItemHandler(this.tile.getContainerInventory(), 5, 29, 63));
        this.addSlot(new SlotItemHandler(this.tile.getContainerInventory(), 6, 63, 65));
        this.addSlot(new SlotItemHandler(this.tile.getContainerInventory(), 7, 97, 63));
        
        this.addSlot(new SlotOutputOnly(this.tile.getContainerInventory(), 8, 145, 50));
        
        this.layoutPlayerInventorySlots(8, 98);
    }
}

package io.github.noeppi_noeppi.mods.faded.mods.keksmagic;

import io.github.noeppi_noeppi.libx.inventory.BaseItemStackHandler;
import io.github.noeppi_noeppi.libx.inventory.ItemStackHandlerWrapper;
import io.github.noeppi_noeppi.libx.mod.registration.TileEntityBase;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.Effect;
import net.minecraft.tileentity.AbstractFurnaceTileEntity;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;

public class CookieAssembler extends TileEntityBase implements ITickableTileEntity {

    public static final int MAX_PROGRESS = 20 * 10;

    private final BaseItemStackHandler inventory;
    private int progress = 0;
    private int burnTicks = 0;
    private int maxBurnTicks = 0;

    private final LazyOptional<IItemHandlerModifiable> capGeneric;
    private final LazyOptional<IItemHandlerModifiable> capCocoa;
    private final LazyOptional<IItemHandlerModifiable> capDoughFuel;
    private final LazyOptional<IItemHandlerModifiable> capOutput;

    public CookieAssembler(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
        this.inventory = new BaseItemStackHandler(9, slot -> this.markDirty(), this::itemValid);
        this.inventory.setInputSlots(0, 1, 2, 3, 4, 5, 6);
        this.inventory.setOutputSlots(8);
        this.inventory.addSlotLimit(0, 1);
        this.inventory.addSlotLimit(1, 1);
        this.inventory.addSlotLimit(2, 1);
        this.inventory.addSlotLimit(3, 1);
        this.inventory.addSlotLimit(4, 1);
        this.capGeneric = ItemStackHandlerWrapper.createLazy(this::getInventory);
        this.capCocoa = ItemStackHandlerWrapper.createLazy(this::getInventory, slot -> false, (slot, stack) -> slot < 5);
        this.capDoughFuel = ItemStackHandlerWrapper.createLazy(this::getInventory, slot -> false, (slot, stack) -> slot >= 5 && slot < 7);
        this.capOutput = ItemStackHandlerWrapper.createLazy(this::getInventory, slot -> slot == 8, (stack, slot) -> false);
    }

    private boolean itemValid(int slot, ItemStack stack) {
        if (slot < 5) {
            return stack.getItem() == Items.COCOA_BEANS || stack.getItem() instanceof ItemMagicalCocoa;
        } else if (slot == 5) {
            return AbstractFurnaceTileEntity.isFuel(stack);
        } else if (slot == 6) {
            return stack.getItem() instanceof ItemCookieDough;
        } else if (slot == 7) {
            return stack.getItem() instanceof ItemCookieCutter;
        } else {
            return false;
        }
    }

    @Override
    public void tick() {
        if (this.world != null && !this.world.isRemote) {
            this.handleFuelLogic();
            ItemStack result = this.getResult();
            ItemStack output = this.inventory.getStackInSlot(8);
            if (result.isEmpty() || (!output.isEmpty() && (!ItemStack.areItemsEqual(result, output) || !ItemStack.areItemStackTagsEqual(result, output) || result.getCount() + output.getCount() > output.getMaxStackSize()))) {
                if (this.progress != 0) {
                    this.progress = 0;
                    this.markDirty();
                    this.markDispatchable();
                }
            } else {
                if (this.burnTicks > 0) {
                    this.progress += 1;
                } else if (this.progress > 0) {
                    this.progress = Math.max(0, this.progress - 3);
                }
                if (this.progress >= MAX_PROGRESS) {
                    this.consumeItem(0);
                    this.consumeItem(1);
                    this.consumeItem(2);
                    this.consumeItem(3);
                    this.consumeItem(4);
                    this.consumeItem(6);
                    if (output.isEmpty()) {
                        this.inventory.setStackInSlot(8, result);
                    } else {
                        ItemStack copy = output.copy();
                        copy.grow(result.getCount());
                        this.inventory.setStackInSlot(8, copy);
                    }
                }
                this.markDirty();
                this.markDispatchable();
            }
        }
    }

    private void handleFuelLogic() {
        if (this.burnTicks > 0) {
            this.burnTicks -= 1;
            this.markDirty();
            this.markDispatchable();
        }
        if (this.burnTicks <= 0) {
            ItemStack fuel = this.inventory.getStackInSlot(5);
            int time = ForgeHooks.getBurnTime(fuel);
            if (time > 0) {
                this.burnTicks = time;
                this.maxBurnTicks = time;
                ItemStack newStack;
                if (fuel.hasContainerItem()) {
                    newStack = fuel.getContainerItem().copy();
                } else {
                    newStack = fuel.copy();
                    newStack.shrink(1);
                }
                this.inventory.setStackInSlot(5, newStack);
                this.markDirty();
                this.markDispatchable();
            } else if (this.maxBurnTicks != 0) {
                this.maxBurnTicks = 0;
                this.markDirty();
                this.markDispatchable();
            }
        }
    }

    private ItemStack getResult() {
        ItemStack cutterStack = this.inventory.getStackInSlot(7);
        if (cutterStack.isEmpty() || !(cutterStack.getItem() instanceof ItemCookieCutter)) {
            return ItemStack.EMPTY;
        }
        ItemCookieCutter.Type type = ((ItemCookieCutter) cutterStack.getItem()).type;
        ItemStack tierStack = this.inventory.getStackInSlot(6);
        if (tierStack.isEmpty() || !(tierStack.getItem() instanceof ItemCookieDough)) {
            return ItemStack.EMPTY;
        }
        int tier = MathHelper.clamp(((ItemCookieDough) tierStack.getItem()).tier, 1, 4);
        if (this.inventory.getStackInSlot(0).isEmpty() || this.inventory.getStackInSlot(1).isEmpty()
                || this.inventory.getStackInSlot(2).isEmpty() || this.inventory.getStackInSlot(3).isEmpty()
                || this.inventory.getStackInSlot(4).isEmpty()) {
            return ItemStack.EMPTY;
        }
        Effect e0 = this.getEffect(0);
        Effect e1 = this.getEffect(1);
        Effect e2 = this.getEffect(2);
        Effect e3 = this.getEffect(3);
        Effect e4 = this.getEffect(4);
        Set<Effect> effects = new HashSet<>();
        effects.add(e0);
        effects.add(e1);
        effects.add(e2);
        effects.add(e3);
        effects.add(e4);
        if (effects.size() > 5 || effects.isEmpty()) {
            return ItemStack.EMPTY;
        }
        return ItemCookie.makeCookie(type, tier, e0, e1, e2, e3, e4);
    }
    
    @Nullable
    private Effect getEffect(int slot) {
        ItemStack stack = this.inventory.getStackInSlot(slot);
        if (!stack.isEmpty() && stack.getItem() instanceof ItemMagicalCocoa) {
            return ((ItemMagicalCocoa) stack.getItem()).effect;
        } else {
            return null;
        }
    }
    
    private void consumeItem(int slot) {
        ItemStack stack = this.inventory.getStackInSlot(slot);
        if (stack.hasContainerItem()) {
            this.inventory.setStackInSlot(slot, stack.getContainerItem().copy());
        } else if (stack.getCount() <= 1) {
            this.inventory.setStackInSlot(slot, ItemStack.EMPTY);
        } else {
            ItemStack copy = stack.copy();
            copy.shrink(1);
            this.inventory.setStackInSlot(slot, copy);
        }
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (side == Direction.UP) {
                return this.capCocoa.cast();
            } else if (side == Direction.DOWN) {
                return this.capOutput.cast();
            } else if (side == Direction.NORTH || side == Direction.SOUTH
                    || side == Direction.EAST || side == Direction.WEST) {
                return this.capDoughFuel.cast();
            } else {
                return this.capGeneric.cast();
            }
        } else {
            return super.getCapability(cap, side);
        }
    }

    @Override
    public void read(@Nonnull BlockState state, @Nonnull CompoundNBT nbt) {
        super.read(state, nbt);
        this.inventory.deserializeNBT(nbt.getCompound("Inventory"));
        this.progress = nbt.getInt("Progress");
        this.burnTicks = nbt.getInt("BurnTicks");
        this.maxBurnTicks = nbt.getInt("MaxBurnTicks");
    }

    @Nonnull
    @Override
    public CompoundNBT write(@Nonnull CompoundNBT nbt) {
        nbt.put("Inventory", this.inventory.serializeNBT());
        nbt.putInt("Progress", this.progress);
        nbt.putInt("BurnTicks", this.burnTicks);
        nbt.putInt("MaxBurnTicks", this.maxBurnTicks);
        return super.write(nbt);
    }

    @Nonnull
    @Override
    public CompoundNBT getUpdateTag() {
        CompoundNBT nbt = super.getUpdateTag();
        if (this.world != null && !this.world.isRemote) {
            nbt.putInt("Progress", this.progress);
            nbt.putInt("BurnTicks", this.burnTicks);
            nbt.putInt("MaxBurnTicks", this.maxBurnTicks);
        }
        return nbt;
    }

    @Override
    public void handleUpdateTag(BlockState state, CompoundNBT nbt) {
        if (this.world != null && this.world.isRemote) {
            this.progress = nbt.getInt("Progress");
            this.burnTicks = nbt.getInt("BurnTicks");
            this.maxBurnTicks = nbt.getInt("MaxBurnTicks");
        }
    }

    public IItemHandlerModifiable getInventory() {
        return this.inventory;
    }
    
    public IItemHandlerModifiable getContainerInventory() {
        return this.inventory.getUnrestricted();
    }

    public int getProgress() {
        return this.progress;
    }

    public int getBurnTicks() {
        return this.burnTicks;
    }

    public int getMaxBurnTicks() {
        return this.maxBurnTicks;
    }
}

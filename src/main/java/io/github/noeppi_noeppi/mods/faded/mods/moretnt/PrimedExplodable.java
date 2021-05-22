package io.github.noeppi_noeppi.mods.faded.mods.moretnt;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.TNTEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

public class PrimedExplodable extends TNTEntity {

    private static final DataParameter<Boolean> BOMB = EntityDataManager.createKey(PrimedExplodable.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Float> POWER = EntityDataManager.createKey(PrimedExplodable.class, DataSerializers.FLOAT);
    private static final DataParameter<Optional<BlockState>> EXPLODABLE = EntityDataManager.createKey(PrimedExplodable.class, DataSerializers.OPTIONAL_BLOCK_STATE);


    private float power;
    private boolean bomb;
    private BlockState explodable;
    
    public PrimedExplodable(World world) {
        this(MoreTNT.primedExplodable, world);
    }
    
    public PrimedExplodable(EntityType<? extends PrimedExplodable> type, World world) {
        super(type, world);
    }
    
    public PrimedExplodable(World world, double x, double y, double z, ExplodableProperties properties, boolean shortFuse, boolean bomb, BlockState explodable, @Nullable LivingEntity igniter) {
        this(MoreTNT.primedExplodable, world);
        this.setPosition(x, y, z);
        double angle = 2 * Math.PI * world.rand.nextDouble();
        this.setMotion(-Math.sin(angle) * properties.initialMotion, properties.initialMotion, -Math.cos(angle) * properties.initialMotion);
        this.setFuse(shortFuse ? properties.reducedFuse(world.rand) : properties.fuse);
        this.setPower(properties.power);
        this.setBomb(bomb);
        this.prevPosX = x;
        this.prevPosY = y;
        this.prevPosZ = z;
        this.setExplodable(explodable);
        this.igniter = igniter;
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(POWER, 4f);
        this.dataManager.register(BOMB, false);
        this.dataManager.register(EXPLODABLE, Optional.empty());
    }
    
    @Override
    protected void onInsideBlock(@Nonnull BlockState state) {
        if (this.ticksExisted > 5 && this.bomb && state.isSolid()) {
            this.remove();
            this.explode();
        }
    }

    @Override
    protected void explode() {
        if (this.explodable.getBlock() instanceof BlockExplodable) {
            if (((BlockExplodable) this.explodable.getBlock()).doExplode(this, this.power)) {
                return;
            }
        }
        this.world.createExplosion(this, this.getPosX(), this.getPosYHeight(0.0625D), this.getPosZ(), this.power, Explosion.Mode.BREAK);
    }

    @Override
    protected void readAdditional(@Nonnull CompoundNBT nbt) {
        super.readAdditional(nbt);
        this.power = nbt.getFloat("ExplosionPower");
        this.bomb = nbt.getBoolean("Bomb");
        this.explodable = Block.getStateById(nbt.getInt("ExplodableState"));
    }
    
    @Override
    protected void writeAdditional(@Nonnull CompoundNBT nbt) {
        super.writeAdditional(nbt);
        nbt.putFloat("ExplosionPower", this.power);
        nbt.putBoolean("Bomb", this.bomb);
        nbt.putInt("ExplodableState", Block.getStateId(this.explodable));
    }
    
    @Nonnull
    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public void notifyDataManagerChange(@Nonnull DataParameter<?> key) {
        super.notifyDataManagerChange(key);
        if (BOMB.equals(key)) {
            this.bomb = this.dataManager.get(BOMB);
        } else if (POWER.equals(key)) {
            this.power = this.dataManager.get(POWER);
        } else if (EXPLODABLE.equals(key)) {
            this.explodable = this.dataManager.get(EXPLODABLE).orElse(null);
        }
    }

    public void setPower(float power) {
        this.dataManager.set(POWER, power);
        this.power = power;
    }

    public void setBomb(boolean bomb) {
        this.dataManager.set(BOMB, bomb);
        this.bomb = bomb;
    }

    public void setExplodable(@Nullable BlockState explodable) {
        this.dataManager.set(EXPLODABLE, Optional.ofNullable(explodable));
        this.explodable = explodable;
    }

    public float getPower() {
        return this.power;
    }

    public boolean isBomb() {
        return this.bomb;
    }

    @Nullable
    public BlockState getExplodable() {
        return this.explodable;
    }
}

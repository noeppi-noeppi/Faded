package io.github.noeppi_noeppi.mods.faded.mods.turtlecraft;

import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.DrownedEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.IPacket;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import javax.annotation.Nonnull;
import java.util.Random;

public class Turtle extends CreatureEntity {
    
    public Turtle(@Nonnull World world) {
        this(TurtleCraft.turtle, world);
    }
    
    public Turtle(EntityType<Turtle> type, World world) {
        super(type, world);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(10, new PanicGoal(this, 0.6));
        this.goalSelector.addGoal(11, new RandomWalkingGoal(this, 0.4));
        this.goalSelector.addGoal(12, new LookAtGoal(this, DrownedEntity.class, 6));
        this.goalSelector.addGoal(12, new LookAtGoal(this, PlayerEntity.class, 6));
        this.goalSelector.addGoal(13, new LookRandomlyGoal(this));
        this.initExtraAI();
    }

    protected void initExtraAI() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
    }

    @Override
    public int getMaxSpawnedInChunk() {
        return 4;
    }

    @Override
    protected float getSoundVolume() {
        return 0.4f;
    }

    @Override
    public int getTalkInterval() {
        return 400;
    }

    @Override
    public boolean isOnLadder() {
        return false;
    }

    @Override
    protected float getStandingEyeHeight(@Nonnull Pose pose, EntitySize sizeIn) {
        return sizeIn.height * 0.85f;
    }

    @Override
    protected int decreaseAirSupply(int air) {
        return 0;
    }

    @Override
    public int getAir() {
        return 10;
    }

    @Override
    protected double followLeashSpeed() {
        return 0.5;
    }

    public static AttributeModifierMap defaultAttributes() {
        return MobEntity.getDefaultAttributes()
                .createMutableAttribute(Attributes.MAX_HEALTH, 8)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.2)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 1)
                .createMutableAttribute(Attributes.ATTACK_KNOCKBACK, 0.1)
                .create();
    }

    public static boolean canSpawnAt(EntityType<Turtle> type, IWorld world, SpawnReason reason, BlockPos pos, Random random) {
        return (reason != SpawnReason.CHUNK_GENERATION && reason != SpawnReason.NATURAL)
                || world.getBiome(pos).getCategory() == Biome.Category.BEACH
                || world.getBiome(pos).getCategory() == Biome.Category.RIVER
                || world.getBiome(pos).getCategory() == Biome.Category.OCEAN;
    }

    @Nonnull
    @Override
    protected ResourceLocation getLootTable() {
        return super.getLootTable();
    }
}

package io.github.noeppi_noeppi.mods.faded.mods.usefultotems;

import io.github.noeppi_noeppi.libx.mod.ModX;
import io.github.noeppi_noeppi.libx.util.BoundingBoxUtils;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class ItemSalvageTotem extends ItemBetterTotem {

    public ItemSalvageTotem(ModX mod, Properties properties) {
        super(mod, properties);
    }

    @Override
    public boolean applyEffect(World world, ItemStack stack, LivingEntity living, DamageSource damage) {
        this.basicTotem(living, 6f);
        if (damage.getTrueSource() instanceof LivingEntity) {
            living.addPotionEffect(new EffectInstance(Effects.STRENGTH, 900, 1));
        }
        if (damage == DamageSource.FALL) {
            living.addPotionEffect(new EffectInstance(Effects.REGENERATION, 100, 2));
            living.addPotionEffect(new EffectInstance(Effects.SLOW_FALLING, 600, 0));
        } else if (damage == DamageSource.CACTUS) {
            BlockPos.Mutable mpos = living.getPosition().up().toMutable();
            while (world.getBlockState(mpos).getBlock() == Blocks.CACTUS) {
                mpos.move(Direction.UP);
            }
			living.setPositionAndUpdate(mpos.getX() + 0.5, mpos.getY() + 0.5, mpos.getZ() + 0.5);
		} else if (damage == DamageSource.CRAMMING) {
            List<Entity> others = world.getEntitiesInAABBexcluding(living, BoundingBoxUtils.expand(living, 3), e -> !e.isSpectator());
            for (Entity other : others) {
                if (other instanceof LivingEntity) {
                    other.attackEntityFrom(DamageSource.CRAMMING, 12);
                }
            }
        } else if (damage == DamageSource.DRAGON_BREATH || damage == DamageSource.MAGIC) {
			living.addPotionEffect(new EffectInstance(Effects.REGENERATION, 600, 2));
            living.addPotionEffect(new EffectInstance(Effects.RESISTANCE, 600, 1));
		} else if (damage == DamageSource.DROWN) {
			living.setAir(10);
			living.addPotionEffect(new EffectInstance(Effects.WATER_BREATHING, 6000, 0));
		} else if (damage == DamageSource.IN_WALL || damage == DamageSource.FLY_INTO_WALL
				|| damage == DamageSource.FALLING_BLOCK || damage == DamageSource.ANVIL) {
			BlockPos pos1 = living.getPosition();
			BlockPos pos2 = living.getPosition().up();
			BlockPos pos3 = living.getPosition().up().up();
			world.destroyBlock(pos3, true);
			world.setBlockState(pos3, Blocks.STONE.getDefaultState(), 3);
			world.destroyBlock(pos2, true);
			world.destroyBlock(pos1, true);
		} else if (damage == DamageSource.LIGHTNING_BOLT) {
			living.forceFireTicks(0);
            living.addPotionEffect(new EffectInstance(Effects.FIRE_RESISTANCE, 1200, 0));
            living.addPotionEffect(new EffectInstance(Effects.GLOWING, 80, 0));
		} else if (damage.isFireDamage()) {
            living.addPotionEffect(new EffectInstance(Effects.FIRE_RESISTANCE, 6000, 0));
		} else if (damage == DamageSource.OUT_OF_WORLD && living.getPosY() < 0) {
            living.addPotionEffect(new EffectInstance(Effects.LEVITATION, 200, 0));
            living.addPotionEffect(new EffectInstance(Effects.LEVITATION, 30, 99));
            living.addPotionEffect(new EffectInstance(Effects.SLOW_FALLING, 800, 0));
            living.setPositionAndUpdate(living.getPosX(), Math.max(living.getPosY(), -20), living.getPosZ());
		} else if (damage == DamageSource.STARVE) {
            if (living instanceof PlayerEntity) {
                ((PlayerEntity) living).getFoodStats().setFoodLevel(20);
                ((PlayerEntity) living).getFoodStats().setFoodSaturationLevel(5);
            }
		} else if (damage == DamageSource.WITHER) {
			if (living.isPotionActive(Effects.WITHER)) {
				living.removePotionEffect(Effects.WITHER);
			}
            living.addPotionEffect(new EffectInstance(Effects.HEALTH_BOOST, 2400, 2));
			living.heal(20);
		} else {
            living.addPotionEffect(new EffectInstance(Effects.REGENERATION, 100, 2));
        }
        return true;
    }
}

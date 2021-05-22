package io.github.noeppi_noeppi.mods.faded.mods.keksmagic;

import com.google.common.collect.ImmutableSet;
import io.github.noeppi_noeppi.libx.mod.ModX;
import io.github.noeppi_noeppi.libx.mod.registration.ItemBase;
import io.github.noeppi_noeppi.libx.util.Misc;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@OnlyIn(value = Dist.CLIENT, _interface = IItemColor.class)
public class ItemCookie extends ItemBase implements IItemColor {

    public static final Set<Effect> NON_LEVELABLE = ImmutableSet.of(
            Effects.NAUSEA, Effects.FIRE_RESISTANCE, Effects.WATER_BREATHING,
            Effects.INVISIBILITY, Effects.BLINDNESS, Effects.NIGHT_VISION,
            Effects.GLOWING, Effects.CONDUIT_POWER, Effects.DOLPHINS_GRACE,
            Effects.HERO_OF_THE_VILLAGE
    );
    
    public ItemCookie(ModX mod, Properties properties) {
        super(mod, properties);
    }
    
    public static ItemCookieCutter.Type getType(ItemStack stack) {
        if (stack.hasTag()) {
            String typeStr = stack.getOrCreateTag().getString("type");
            try {
                return ItemCookieCutter.Type.valueOf(typeStr);
            } catch (IllegalArgumentException e) {
                return ItemCookieCutter.Type.SELF;
            }
        } else {
            return ItemCookieCutter.Type.SELF;
        }
    }
    
    public static int getTier(ItemStack stack) {
        if (stack.hasTag()) {
            return MathHelper.clamp(stack.getOrCreateTag().getInt("tier"), 1, 4);
        } else {
            return 1;
        }
    }
    
    @Nullable
    public static Effect getEffect(ItemStack stack, int idx) {
        if (stack.hasTag()) {
            String key = "effect" + idx;
            String rlStr = stack.getOrCreateTag().getString(key);
            ResourceLocation rl = ResourceLocation.tryCreate(rlStr);
            if (rl == null || !ForgeRegistries.POTIONS.containsKey(rl)) {
                return null;
            } else {
                return ForgeRegistries.POTIONS.getValue(rl);
            }
        } else {
            return null;
        }
    }
    
    public static ItemStack makeCookie(ItemCookieCutter.Type type, int tier, Effect e0, Effect e1, Effect e2, Effect e3, Effect e4) {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putString("type", type.name());
        nbt.putInt("tier", MathHelper.clamp(tier, 1, 4));
        nbt.putString("effect0", Optional.ofNullable(ForgeRegistries.POTIONS.getKey(e0)).orElse(Misc.MISSIGNO).toString());
        nbt.putString("effect1", Optional.ofNullable(ForgeRegistries.POTIONS.getKey(e1)).orElse(Misc.MISSIGNO).toString());
        nbt.putString("effect2", Optional.ofNullable(ForgeRegistries.POTIONS.getKey(e2)).orElse(Misc.MISSIGNO).toString());
        nbt.putString("effect3", Optional.ofNullable(ForgeRegistries.POTIONS.getKey(e3)).orElse(Misc.MISSIGNO).toString());
        nbt.putString("effect4", Optional.ofNullable(ForgeRegistries.POTIONS.getKey(e4)).orElse(Misc.MISSIGNO).toString());
        ItemStack stack = new ItemStack(KeksMagic.cookie);
        stack.setTag(nbt);
        return stack;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public int getColor(@Nonnull ItemStack stack, int tint) {
        if (tint == 0) {
            int c = 255 - ((getTier(stack) - 1) * 30);
            return (c << 16) | (c << 8) | c;
        } else if (tint <= 5) {
            Effect effect = getEffect(stack, tint - 1);
            if (effect == null) {
                return 0x4B2817;
            } else {
                return effect.getLiquidColor();
            }
        } else {
            return 0xFFFFFF;
        }
    }
    
    public static List<EffectInstance> getEffects(ItemStack stack) {
        List<Effect> potions = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Effect potion = getEffect(stack, i);
            if (potion != null) {
                potions.add(potion);
            }
        }
        List<EffectInstance> effects = new ArrayList<>();
        float strength = getTier(stack) * (10f / potions.size()) + 0.5f;
        for (Effect potion : potions) {
            if (potion.isInstant()) {
                effects.add(new EffectInstance(potion, 1, Math.max(0, Math.round(strength / 3))));
            } else if (NON_LEVELABLE.contains(potion)) {
                effects.add(new EffectInstance(potion, Math.max(20, Math.round(strength * 70)), 0));
            } else {
                effects.add(new EffectInstance(potion, Math.max(20, Math.round(strength * 25)), Math.max(0, Math.round(strength / 7))));
            }
        }
        return effects;
    }

    @Override
    @Nonnull
    public ItemStack onItemUseFinish(@Nonnull ItemStack stack, @Nonnull World world, @Nonnull LivingEntity living) {
        if (living instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) living;
            if (player instanceof ServerPlayerEntity) {
                CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayerEntity) player, stack);
            }
            player.addStat(Stats.ITEM_USED.get(this));
            if (!world.isRemote) {
                List<EffectInstance> effects = getEffects(stack);
                getType(stack).apply(player, effects);
            }
            if (!player.isCreative()) {
                stack.shrink(1);
            }
        }
        return stack;
    }

    @Override
    public int getUseDuration(@Nonnull ItemStack stack) {
        return 48 / MathHelper.clamp(getTier(stack), 1, 4);
    }

    @Override
    @Nonnull
    public UseAction getUseAction(@Nonnull ItemStack stack) {
        return UseAction.EAT;
    }

    @Override
    @Nonnull
    public ActionResult<ItemStack> onItemRightClick(@Nonnull World world, @Nonnull PlayerEntity player, @Nonnull Hand hand) {
        player.setActiveHand(hand);
        return ActionResult.resultConsume(player.getHeldItem(hand));
    }
}

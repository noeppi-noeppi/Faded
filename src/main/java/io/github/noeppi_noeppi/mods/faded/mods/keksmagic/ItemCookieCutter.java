package io.github.noeppi_noeppi.mods.faded.mods.keksmagic;

import io.github.noeppi_noeppi.libx.mod.ModX;
import io.github.noeppi_noeppi.libx.mod.registration.ItemBase;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.BowItem;
import net.minecraft.potion.EffectInstance;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;

import java.util.List;

public class ItemCookieCutter extends ItemBase {

    public final Type type;
    
    public ItemCookieCutter(ModX mod, Type type, Properties properties) {
        super(mod, properties);
        this.type = type;
    }
    
    public enum Type {
        SELF,
        PROJECTILE;
        
        public void apply(PlayerEntity player, List<EffectInstance> effects) {
            if (this == PROJECTILE) {
                ArrowEntity arrow = new ArrowEntity(player.world, player);
                arrow.setDirectionAndMovement(player, player.rotationPitch, player.rotationYaw, 0, 5, 1);
                arrow.setDamage(0);
                for (EffectInstance effect : effects) {
                    arrow.addEffect(effect);
                }
                arrow.pickupStatus = AbstractArrowEntity.PickupStatus.CREATIVE_ONLY;
                player.world.addEntity(arrow);
            } else {
                for (EffectInstance effect : effects) {
                    player.addPotionEffect(effect);
                }
            }
        }
    }
}

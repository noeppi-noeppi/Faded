package io.github.noeppi_noeppi.mods.faded.mods.extrabaubles;

import com.google.common.collect.ImmutableList;
import io.github.noeppi_noeppi.libx.mod.ModX;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import top.theillusivec4.curios.api.SlotTypePreset;

import java.util.List;

public class UneffectBauble extends ItemBauble {

    private final List<Effect> effects;
    
    public UneffectBauble(ModX mod, SlotTypePreset slot, Effect... effects) {
        this(mod, slot, new Item.Properties(), effects);
    }

    public UneffectBauble(ModX mod, SlotTypePreset slot, Properties properties, Effect... effects) {
        super(mod, slot, properties.group(ExtraBaubles.TAB).maxStackSize(1));
        this.effects = ImmutableList.copyOf(effects);
    }

    @Override
    public void tick(ItemStack stack, LivingEntity entity) {
        for (Effect effect : this.effects) {
            entity.removePotionEffect(effect);
        }
    }
}

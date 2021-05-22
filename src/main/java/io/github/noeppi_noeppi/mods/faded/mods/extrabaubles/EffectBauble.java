package io.github.noeppi_noeppi.mods.faded.mods.extrabaubles;

import com.google.common.collect.ImmutableList;
import io.github.noeppi_noeppi.libx.mod.ModX;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import top.theillusivec4.curios.api.SlotTypePreset;

import java.util.List;

public class EffectBauble extends ItemBauble {
    
    private final List<Effect> effects;
    private final int amplifierFirst;

    public EffectBauble(ModX mod, SlotTypePreset slot, Effect... effects) {
        this(mod, slot, 0, effects);
    }

    public EffectBauble(ModX mod, SlotTypePreset slot, Properties properties, Effect... effects) {
        this(mod, slot, properties, 0, effects);
    }
    
    public EffectBauble(ModX mod, SlotTypePreset slot, int amplifierFirst, Effect... effects) {
        this(mod, slot, new Item.Properties(), amplifierFirst, effects);
    }

    public EffectBauble(ModX mod, SlotTypePreset slot, Properties properties, int amplifierFirst, Effect... effects) {
        super(mod, slot, properties.group(ExtraBaubles.TAB).maxStackSize(1));
        this.effects = ImmutableList.copyOf(effects);
        this.amplifierFirst = amplifierFirst;
    }

    @Override
    public void tick(ItemStack stack, LivingEntity entity) {
        for (int i = 0; i < this.effects.size(); i++) {
            entity.addPotionEffect(new EffectInstance(this.effects.get(i), 10, i == 0 ? this.amplifierFirst : 0, false, false));
        }
    }
}

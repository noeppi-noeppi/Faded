package io.github.noeppi_noeppi.mods.faded.mods.extrabaubles;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import io.github.noeppi_noeppi.libx.mod.ModX;
import io.github.noeppi_noeppi.libx.mod.registration.ItemBase;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import top.theillusivec4.curios.api.SlotTypePreset;

import javax.annotation.Nullable;

public class ItemBauble extends ItemBase {

    public final SlotTypePreset slot;

    public ItemBauble(ModX mod, SlotTypePreset slot) {
        this(mod, slot, new Item.Properties());
    }
    
    public ItemBauble(ModX mod, SlotTypePreset slot, Properties properties) {
        super(mod, properties.maxStackSize(1).group(ExtraBaubles.TAB));
        this.slot = slot;
    }

    public void equipped(ItemStack stack, LivingEntity entity) {
        
    }
    
    public void unequipped(ItemStack stack, LivingEntity entity) {
        
    }
    
    public void tick(ItemStack stack, LivingEntity entity) {
        
    }
    
    public boolean canEquip(ItemStack stack, LivingEntity entity) {
        return true;
    }
    
    public boolean canUnequip(ItemStack stack, LivingEntity entity) {
        return true;
    }
    
    public Multimap<Attribute, AttributeModifier> baubleAttributes(ItemStack stack) {
        return ImmutableMultimap.of();
    }
    
    @Nullable
	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
		return BaubleWrappers.caps(stack);
	}
}

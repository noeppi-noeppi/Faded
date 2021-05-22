package io.github.noeppi_noeppi.mods.faded.mods.extrabaubles;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import io.github.noeppi_noeppi.libx.mod.ModX;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import top.theillusivec4.curios.api.SlotTypePreset;

import java.util.UUID;

public class LifeAmulet extends ItemBauble {

    private final Multimap<Attribute, AttributeModifier> attributes;

    public LifeAmulet(ModX mod, SlotTypePreset slot, int maxLife) {
        this(mod, slot, maxLife, new Item.Properties());
    }

    public LifeAmulet(ModX mod, SlotTypePreset slot, int maxLife, Properties properties) {
        super(mod, slot, properties.group(ExtraBaubles.TAB).maxStackSize(1));
        this.attributes = ImmutableMultimap.of(
                Attributes.MAX_HEALTH, new AttributeModifier(
                        new UUID(0x3F6C1F2C674E334Dl, 0x35D8705El * maxLife),
                        "faded_extrabaubles_amulet" + maxLife + "_life", maxLife,
                        AttributeModifier.Operation.ADDITION
                )
        );
    }

    @Override
    public Multimap<Attribute, AttributeModifier> baubleAttributes(ItemStack stack) {
        return this.attributes;
    }
}

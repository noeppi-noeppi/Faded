package io.github.noeppi_noeppi.mods.faded.data;

import io.github.noeppi_noeppi.libx.data.provider.BlockTagProviderBase;
import io.github.noeppi_noeppi.libx.data.provider.ItemTagProviderBase;
import io.github.noeppi_noeppi.libx.mod.ModX;
import io.github.noeppi_noeppi.mods.faded.mods.extrabaubles.ItemBauble;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;
import top.theillusivec4.curios.api.SlotTypePreset;

import java.util.HashMap;
import java.util.Map;

public class ItemTagProvider extends ItemTagProviderBase {

    private final Map<SlotTypePreset, ITag.INamedTag<Item>> tagMap = new HashMap<>();
    
    public ItemTagProvider(ModX mod, DataGenerator generatorIn, ExistingFileHelper fileHelper, BlockTagProviderBase blockTags) {
        super(mod, generatorIn, fileHelper, blockTags);
    }

    @Override
    protected void setup() {
        
    }

    @Override
    public void defaultItemTags(Item item) {
        if (item instanceof ItemBauble) {
            SlotTypePreset slot = ((ItemBauble) item).slot;
            if (!this.tagMap.containsKey(slot)) {
                this.tagMap.put(slot, ItemTags.makeWrapperTag(new ResourceLocation("curios", slot.getIdentifier()).toString()));
            }
            this.getOrCreateBuilder(this.tagMap.get(slot)).add(item);
        }
        super.defaultItemTags(item);
    }
}

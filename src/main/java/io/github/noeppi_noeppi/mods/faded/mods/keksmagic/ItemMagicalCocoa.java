package io.github.noeppi_noeppi.mods.faded.mods.keksmagic;

import io.github.noeppi_noeppi.libx.mod.ModX;
import io.github.noeppi_noeppi.libx.mod.registration.ItemBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nonnull;

public class ItemMagicalCocoa extends ItemBase {

    public final Effect effect;
    
    public ItemMagicalCocoa(ModX mod, Effect effect, Properties properties) {
        super(mod, properties);
        this.effect = effect;
    }

    @Nonnull
    @Override
    public ITextComponent getName() {
        return new TranslationTextComponent("item.faded.keksmagic_cocoa", this.effect.getDisplayName());
    }

    @Nonnull
    @Override
    public ITextComponent getDisplayName(@Nonnull ItemStack stack) {
        return new TranslationTextComponent("item.faded.keksmagic_cocoa", this.effect.getDisplayName());
    }
}

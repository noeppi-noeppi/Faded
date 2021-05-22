package io.github.noeppi_noeppi.mods.faded.mods.extrabaubles;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.type.capability.ICurio;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.function.Consumer;
import java.util.function.Function;

public class BaubleWrappers {

    public static ICapabilityProvider caps(ItemStack stack) {
        LazyOptional<BaubleWrapper> wrapper = LazyOptional.of(() -> new BaubleWrapper(stack));
        return new ICapabilityProvider() {
            @Nonnull
            @Override
            public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
                return cap == CuriosCapability.ITEM ? wrapper.cast() : LazyOptional.empty();
            }
        };
    }

    private static class BaubleWrapper implements ICurio {

        private final ItemStack stack;
        // null means we don't know yet about the equipped status.
        private Boolean isEquipped = null;
        
        public BaubleWrapper(ItemStack stack) {
            this.stack = stack;
        }
        
        private void run(Consumer<ItemBauble> action) {
            Item item = this.stack.getItem();
            if (item instanceof ItemBauble) {
                action.accept((ItemBauble) item);
            }
        }
        
        private <T> T run(Function<ItemBauble, T> action, T defaultValue) {
            Item item = this.stack.getItem();
            if (item instanceof ItemBauble) {
                return action.apply((ItemBauble) item);
            } else {
                return defaultValue;
            }
        }
        
        @Override
		public void curioTick(String identifier, int index, LivingEntity living) {
            if (this.isEquipped == null) {
                // Maybe we are already equipped. In that case just skip the first tick call.
                // and set isEquipped to true
                this.isEquipped = true;
            } else if (this.isEquipped) {
                this.run(item -> item.tick(this.stack, living));
            }
		}

		@Override
		public void onEquip(String identifier, int index, LivingEntity living) {
            this.isEquipped = true;
            this.run(item -> item.equipped(this.stack, living));
		}

		@Override
		public void onUnequip(String identifier, int index, LivingEntity living) {
            this.run(item -> item.unequipped(this.stack, living));
            this.isEquipped = false;
        }

		@Override
		public boolean canEquip(String identifier, LivingEntity living) {
			return this.run(item -> item.canEquip(this.stack, living), true);
		}

        @Override
        public boolean canUnequip(String identifier, LivingEntity living) {
            return this.run(item -> item.canUnequip(this.stack, living), true);
        }

        @Override
		public Multimap<Attribute, AttributeModifier> getAttributeModifiers(String identifier) {
            return this.run(item -> item.baubleAttributes(this.stack), ImmutableMultimap.of());
		}

		@Override
		public boolean canSync(String identifier, int index, LivingEntity living) {
			return true;
		}

		@Override
		public boolean canRightClickEquip() {
			return true;
		}
    }
}

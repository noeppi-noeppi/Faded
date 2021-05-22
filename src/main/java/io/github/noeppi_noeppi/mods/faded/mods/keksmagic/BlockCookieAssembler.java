package io.github.noeppi_noeppi.mods.faded.mods.keksmagic;

import io.github.noeppi_noeppi.libx.mod.ModX;
import io.github.noeppi_noeppi.libx.mod.registration.BlockGUI;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;

public class BlockCookieAssembler extends BlockGUI<CookieAssembler, ContainerCookieAssembler> {

    public BlockCookieAssembler(ModX mod, Class<CookieAssembler> teClass, ContainerType<ContainerCookieAssembler> container, Properties properties) {
        super(mod, teClass, container, properties);
    }

    public BlockCookieAssembler(ModX mod, Class<CookieAssembler> teClass, ContainerType<ContainerCookieAssembler> container, Properties properties, Item.Properties itemProperties) {
        super(mod, teClass, container, properties, itemProperties);
    }
}

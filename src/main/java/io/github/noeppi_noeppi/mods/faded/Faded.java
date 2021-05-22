package io.github.noeppi_noeppi.mods.faded;

import io.github.noeppi_noeppi.libx.mod.registration.ModXRegistration;
import io.github.noeppi_noeppi.mods.faded.data.DataGenerators;
import io.github.noeppi_noeppi.mods.faded.mods.extrabaubles.ExtraBaubles;
import io.github.noeppi_noeppi.mods.faded.mods.keksmagic.KeksMagic;
import io.github.noeppi_noeppi.mods.faded.mods.moretnt.MoreTNT;
import io.github.noeppi_noeppi.mods.faded.mods.steelcraftio.SteelCraftIO;
import io.github.noeppi_noeppi.mods.faded.mods.turtlecraft.TurtleCraft;
import io.github.noeppi_noeppi.mods.faded.network.FadedNetwork;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import top.theillusivec4.curios.api.SlotTypeMessage;
import top.theillusivec4.curios.api.SlotTypePreset;

import javax.annotation.Nonnull;

@Mod("faded")
public class Faded extends ModXRegistration {

    private static Faded instance;
    private static FadedNetwork network;
    
    public Faded() {
        // No itemgroup as we have multiple ones
        super("faded", null);
        
        instance = this;
        network = new FadedNetwork(this);
        
        FMLJavaModLoadingContext.get().getModEventBus().addListener(DataGenerators::gatherData);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::sendImc);
        
        SteelCraftIO.init();
        TurtleCraft.init();
        MoreTNT.init();
        ExtraBaubles.init();
        KeksMagic.init();
    }

    @Nonnull
    public static Faded getInstance() {
        return instance;
    }

    @Nonnull
    public static FadedNetwork getNetwork() {
        return network;
    }

    @Override
    protected void setup(FMLCommonSetupEvent fmlCommonSetupEvent) {
        TurtleCraft.setup();
        MoreTNT.setup();
        KeksMagic.setup();
    }

    @Override
    protected void clientSetup(FMLClientSetupEvent fmlClientSetupEvent) {
        TurtleCraft.clientSetup();
        MoreTNT.clientSetup();
        KeksMagic.clientSetup();
    }

    public void sendImc(InterModEnqueueEvent evt) {
        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.CHARM.getMessageBuilder().build());
        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.RING.getMessageBuilder().size(2).build());
        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.BELT.getMessageBuilder().build());
    }
}

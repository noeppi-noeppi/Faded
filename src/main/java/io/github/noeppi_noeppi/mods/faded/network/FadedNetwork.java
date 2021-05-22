package io.github.noeppi_noeppi.mods.faded.network;

import io.github.noeppi_noeppi.libx.mod.ModX;
import io.github.noeppi_noeppi.libx.network.NetworkX;

public class FadedNetwork extends NetworkX {

    public FadedNetwork(ModX mod) {
        super(mod);
    }

    @Override
    protected String getProtocolVersion() {
        return "1";
    }

    @Override
    protected void registerPackets() {

    }
}

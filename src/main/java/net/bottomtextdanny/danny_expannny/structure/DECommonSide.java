package net.bottomtextdanny.danny_expannny.structure;

import net.bottomtextdanny.braincell.mod.structure.AbstractModSide;
import net.bottomtextdanny.danny_expannny.network.DEPacketInitialization;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class DECommonSide extends AbstractModSide {
    public final Logger logger;

    private DECommonSide(String modId) {
        super(modId);
        this.logger = LogManager.getLogger(String.join(modId, "(common content)"));
        DEPacketInitialization.initializeNetworkPackets();
    }

    public static DECommonSide with(String modId) {
        return new DECommonSide(modId);
    }

    @Override
    public void modLoadingCallOut() {

    }

    @Override
    public void postModLoadingPhaseCallOut() {

    }
}

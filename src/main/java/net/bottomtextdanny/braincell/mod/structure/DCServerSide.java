package net.bottomtextdanny.braincell.mod.structure;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.DEDICATED_SERVER)
public final class DCServerSide extends AbstractModSide {

    public final Logger logger;

    public DCServerSide(String modId) {
        super(modId);
        this.logger = LogManager.getLogger(String.join(modId, "(dedicated server content)"));
    }

    public static DCServerSide with(String modId) {
        return new DCServerSide(modId);
    }

    @Override
    public void modLoadingCallOut() {

    }

    @Override
    public void postModLoadingPhaseCallOut() {

    }
}

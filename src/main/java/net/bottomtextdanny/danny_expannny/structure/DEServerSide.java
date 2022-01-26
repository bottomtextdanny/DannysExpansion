package net.bottomtextdanny.danny_expannny.structure;

import net.bottomtextdanny.braincell.mod.structure.AbstractModSide;
import net.bottomtextdanny.danny_expannny.structure.server_sided.PhaseAttributeTransformers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.DEDICATED_SERVER)
public final class DEServerSide extends AbstractModSide {
    public final Logger logger;
    private PhaseAttributeTransformers phaseAttributeTransformers;

    private DEServerSide(String modId) {
        super(modId);
        this.logger = LogManager.getLogger(String.join(modId, "(dedicated server content)"));
    }

    public static DEServerSide with(String modId) {
        return new DEServerSide(modId);
    }

    @Override
    public void modLoadingCallOut() {

    }

    @Override
    public void postModLoadingPhaseCallOut() {
        this.phaseAttributeTransformers = new PhaseAttributeTransformers();
    }

    public PhaseAttributeTransformers getPhaseAttributeTransformers() {
        return this.phaseAttributeTransformers;
    }
}

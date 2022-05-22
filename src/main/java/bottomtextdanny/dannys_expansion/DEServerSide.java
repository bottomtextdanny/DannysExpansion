package bottomtextdanny.dannys_expansion;

import bottomtextdanny.dannys_expansion.tables.PhaseAttributeTransformers;
import bottomtextdanny.braincell.mod._base.AbstractModSide;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class DEServerSide extends AbstractModSide {
    public final Logger logger;

    private PhaseAttributeTransformers phaseAttributeTransformers;

    public DEServerSide(String modId) {
        super(modId);
        this.logger = LogManager.getLogger(modId + "(dedicated server content)");
    }

    @Override
    public void modLoadingCallOut() {}

    @Override
    public void postModLoadingPhaseCallOut() {
        this.phaseAttributeTransformers = new PhaseAttributeTransformers();
    }

    public PhaseAttributeTransformers getPhaseAttributeTransformers() {
        return this.phaseAttributeTransformers;
    }
}

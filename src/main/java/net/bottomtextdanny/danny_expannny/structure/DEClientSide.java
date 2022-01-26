package net.bottomtextdanny.danny_expannny.structure;

import net.bottomtextdanny.braincell.mod.structure.AbstractModSide;
import net.bottomtextdanny.danny_expannny.structure.client_sided.gun_rendering.GunRenderData;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public final class DEClientSide extends AbstractModSide {
    public final Logger logger;
    private final GunRenderData gunRenderData;

    private DEClientSide(String modId) {
        super(modId);
        this.logger = LogManager.getLogger(String.join(modId, "(client content)"));
        this.gunRenderData = new GunRenderData();
    }

    public static DEClientSide with(String modId) {
        return new DEClientSide(modId);
    }

    @Override
    public void modLoadingCallOut() {

    }

    @Override
    public void postModLoadingPhaseCallOut() {

    }

    public GunRenderData getGunRenderData() {
        return this.gunRenderData;
    }
}

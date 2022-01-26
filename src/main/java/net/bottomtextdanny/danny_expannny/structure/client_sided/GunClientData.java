package net.bottomtextdanny.danny_expannny.structure.client_sided;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public final class GunClientData {
    public double
            renderDispersion,
            renderDispersionO,
            dispersion;
    public float
            pitch,
            pitchO,
            yaw,
            yawO,
            itemUseCountO,
            recoil,
            recoilO,
            recoilSubtract,
            recoilMult,
            pitchRecoil,
            pitchRecoilO,
            pitchRecoilSubtract,
            pitchRecoilMult;
    public int
            rollDirection,
            retrieveFactor;

    public GunClientData() {
        super();
    }
}

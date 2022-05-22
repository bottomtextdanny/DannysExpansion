package bottomtextdanny.dannys_expansion._util;

import bottomtextdanny.dannys_expansion._base.particle.DannyParticleData;
import bottomtextdanny.dannys_expansion.tables._client.DEParticles;

public final class DEParticleUtil {

    public static DannyParticleData dust(boolean lit, int colorCode, float startingSize,
                                  float movementReduction, float sizeReduction) {
        return new DannyParticleData(DEParticles.DUST, colorCode, startingSize, movementReduction, sizeReduction, lit);
    }
    private DEParticleUtil() {}
}

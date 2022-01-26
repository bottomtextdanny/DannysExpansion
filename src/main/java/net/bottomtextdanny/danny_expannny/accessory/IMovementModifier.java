package net.bottomtextdanny.danny_expannny.accessory;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface IMovementModifier {

    boolean cancelFall(float distance, float damageMult);

    default float fallDamageMultModifier(float distance, float damageMult) {
        return damageMult;
    }

    default float fallDistanceModifier(float distance, float damageMult) {
        return distance;
    }
	
    @OnlyIn(Dist.CLIENT)
	default float walkSpeedMult(float speed) {
		return speed;
	}
}

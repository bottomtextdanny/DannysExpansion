package net.bottomtextdanny.danny_expannny.accessory.modules;

public interface FinnFall {

    boolean cancelFall(float distance, float damageMult);

    default float fallDamageMultModifier(float distance, float damageMult) {
        return damageMult;
    }

    default float fallDistanceModifier(float distance, float damageMult) {
        return distance;
    }
}

package net.bottomtextdanny.danny_expannny.accessory.modules;

import net.minecraft.world.damagesource.DamageSource;
import org.apache.commons.lang3.mutable.MutableObject;

public interface FinnHurt {

    void onHurt(DamageSource source, MutableObject<Float> amount);
}

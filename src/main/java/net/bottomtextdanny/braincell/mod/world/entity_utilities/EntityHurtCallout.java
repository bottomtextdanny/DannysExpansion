package net.bottomtextdanny.braincell.mod.world.entity_utilities;

import net.minecraft.world.damagesource.DamageSource;

public interface EntityHurtCallout {
	
	float hurtCallout(float damage, DamageSource source);
}

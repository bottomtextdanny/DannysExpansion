package net.bottomtextdanny.dannys_expansion.core.base.item;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;

public interface ISweepAnimation {
	
	default void sweepParticleHook(Player player) {
		double d0 = -Mth.sin(player.getYRot() * ((float) Math.PI / 180F));
		double d1 = Mth.cos(player.getYRot() * ((float) Math.PI / 180F));
		if (player.level instanceof ServerLevel) {
			((ServerLevel) player.level).sendParticles(getSweepParticle(player), player.getX() + d0 * 1.3, player.getY(0.5D), player.getZ() + d1 * 1.3, 0, d0, 0.0D, d1, 0.0D);
		}
	}
	
	default ParticleOptions getSweepParticle(Player player) {
		return ParticleTypes.SWEEP_ATTACK;
	}
}

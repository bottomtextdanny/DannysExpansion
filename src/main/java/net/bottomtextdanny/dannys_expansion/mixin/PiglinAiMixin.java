package net.bottomtextdanny.dannys_expansion.mixin;

import net.bottomtextdanny.dannys_expansion.common.Evaluations;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PiglinAi.class)
public abstract class PiglinAiMixin {
	
	@Inject(at = @At(value = "HEAD"), method = "isWearingGold", remap = false, cancellable = true)
	private static void wearingGold(LivingEntity entity, CallbackInfoReturnable<Boolean> cir) {
		if (entity instanceof Player player) {
			if (Evaluations.COOL_WITH_PIGLINS.test(player)) {
				cir.setReturnValue(true);
			}
		}
	}
	
	@Inject(at = @At(value = "HEAD"), method = "angerNearbyPiglins", remap = false, cancellable = true)
	private static void angerNearbyPiglins(Player player, boolean idk, CallbackInfo ci) {
		if (Evaluations.COOL_WITH_PIGLINS.test(player)) {
			ci.cancel();
		}
	}
}

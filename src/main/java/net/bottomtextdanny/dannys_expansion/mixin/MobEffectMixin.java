package net.bottomtextdanny.dannys_expansion.mixin;

import net.bottomtextdanny.dannys_expansion.common.Evaluations;
import net.bottomtextdanny.danny_expannny.objects.accessories.AntitoxinAccessory;
import net.bottomtextdanny.danny_expannny.capabilities.player.PlayerHelper;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(MobEffect.class)
public class MobEffectMixin {
	
	@Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z"), method = "applyEffectTick", remap = false)
	public boolean de_poisonHurt(LivingEntity livingentity, DamageSource source, float damageAmount) {
		if ((Object)this == MobEffects.POISON) {
			label : {
				if (livingentity instanceof Player player) {
					AntitoxinAccessory antitoxinInst = PlayerHelper.getAccessory(player, AntitoxinAccessory.class);
					if (antitoxinInst != null ) {
						if (antitoxinInst.switchThenGet()) {
							livingentity.hurt(source, Evaluations.POISON_DAMAGE_MODIFIER.test(player, damageAmount));
						}
						break label;
					}
					
					livingentity.hurt(source, Evaluations.POISON_DAMAGE_MODIFIER.test(player, damageAmount));
					break label;
				}
				livingentity.hurt(source, damageAmount);
			}
		}
		return false;
	}
	
}

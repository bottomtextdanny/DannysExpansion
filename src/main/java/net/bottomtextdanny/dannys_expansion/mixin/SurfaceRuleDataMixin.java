package net.bottomtextdanny.dannys_expansion.mixin;

import net.bottomtextdanny.dannys_expansion.common.Level.end_biome_generation.EndBiomeSurfaceRules;
import net.minecraft.data.worldgen.SurfaceRuleData;
import net.minecraft.world.level.levelgen.SurfaceRules;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SurfaceRuleData.class)
public class SurfaceRuleDataMixin {
	
	@Shadow @Final private static SurfaceRules.RuleSource ENDSTONE;
	
	@Shadow @Final private static SurfaceRules.RuleSource DIRT;
	
	@Inject(at = @At("RETURN"), method = "end", cancellable = true, remap = false)
	private static void injectCustomSurface(CallbackInfoReturnable<SurfaceRules.RuleSource> cir) {
		cir.setReturnValue(SurfaceRules.sequence(EndBiomeSurfaceRules.makeEmossenceGeneration(), cir.getReturnValue()));
	}
}

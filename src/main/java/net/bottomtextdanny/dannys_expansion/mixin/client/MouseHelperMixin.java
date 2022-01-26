package net.bottomtextdanny.dannys_expansion.mixin.client;

import net.bottomtextdanny.braincell.mod.structure.BraincellModules;
import net.bottomtextdanny.dannys_expansion.core.Util.MixinMethods;
import net.minecraft.client.MouseHandler;
import net.minecraft.util.SmoothDouble;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MouseHandler.class)
public class MouseHelperMixin {

	@Shadow private double accumulatedDX;
	@Shadow private double accumulatedDY;
	@Shadow @Final private SmoothDouble smoothTurnX = new SmoothDouble();
	@Shadow @Final private SmoothDouble smoothTurnY = new SmoothDouble();
	@Shadow private boolean mouseGrabbed;
	@Shadow private double lastMouseEventTime = Double.MIN_VALUE;

	@Inject(at = @At(value = "HEAD"), method = "turnPlayer", remap = false)
	public void updatePlayerLook(CallbackInfo ci) {

		if (BraincellModules.MOUSE_HOOKS.isActive()) {

		}
		MixinMethods.updatePlayerLook(this.accumulatedDX, this.accumulatedDY, this.smoothTurnX, this.smoothTurnY, this.mouseGrabbed, this.lastMouseEventTime);
	}
}

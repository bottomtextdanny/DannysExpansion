package net.bottomtextdanny.dannys_expansion.mixin.client;

import net.bottomtextdanny.braincell.Braincell;
import net.bottomtextdanny.dannys_expansion.core.Util.qol.DEUtil;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererMixin {

    @Inject(at = @At(value = "HEAD"), method = "render", remap = false)
    public void updateCameraAndRender(float partialTick, long nanoTime, boolean renderWorldIn, CallbackInfo ci) {
        Braincell.client().getRenderingHandler().setDataOutdated();
        Braincell.client().getRenderingHandler().captureInitialization(partialTick);
        DEUtil.PARTIAL_TICK = partialTick;
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/LevelRenderer;doEntityOutline()V", shift = At.Shift.BEFORE), method = "render", remap = false)
    public void postProcessing(float partialTicks, long nanoTime, boolean renderWorldIn, CallbackInfo ci) {
        Braincell.client().getRenderingHandler().postProcessing();
    }
}

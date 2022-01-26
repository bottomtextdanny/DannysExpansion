package net.bottomtextdanny.dannys_expansion.mixin.client;

import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.entity.EntityType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(EntityRenderers.class)
public class EntityRenderersMixin {
    private static boolean registeredDECustomRenderers;
	
    @Inject(at = @At(value = "HEAD"), method = "createEntityRenderers", remap = 1 == 2)
    private static void createEntityRenderers(EntityRendererProvider.Context p_174050_, CallbackInfoReturnable<Map<EntityType<?>, EntityRenderer<?>>> cir) {
        if (!registeredDECustomRenderers) {
            DannysExpansion.clientManager().registerEntityRenderer();
            registeredDECustomRenderers = true;
        }
    }
}

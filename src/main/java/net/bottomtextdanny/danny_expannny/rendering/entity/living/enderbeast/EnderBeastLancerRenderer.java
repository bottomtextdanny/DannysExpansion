package net.bottomtextdanny.danny_expannny.rendering.entity.living.enderbeast;

import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.danny_expannny.vertex_models.living_entities.ender_beasts.EnderBeastLancerModel;
import net.bottomtextdanny.danny_expannny.objects.entities.mob.ender_beast.EnderBeastLancerEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public class EnderBeastLancerRenderer extends MobRenderer<EnderBeastLancerEntity, EnderBeastLancerModel> {
    public static final ResourceLocation TEXTURES = new ResourceLocation(DannysExpansion.ID, "textures/entity/ender_beast/ender_beast_lancer.png");

    public EnderBeastLancerRenderer(Object manager) {
        this((EntityRendererProvider.Context) manager);
    }

    public EnderBeastLancerRenderer(EntityRendererProvider.Context manager) {
        super(manager, new EnderBeastLancerModel(), 1.3F);
    }

    public ResourceLocation getTextureLocation(EnderBeastLancerEntity entity) {
        return TEXTURES;
    }

    public static int getPackedOverlay(LivingEntity livingEntityIn, float uIn) {
        return OverlayTexture.pack(OverlayTexture.u(uIn), OverlayTexture.v(livingEntityIn.hurtTime > 0 || livingEntityIn.deathTime > 0));
    }
}

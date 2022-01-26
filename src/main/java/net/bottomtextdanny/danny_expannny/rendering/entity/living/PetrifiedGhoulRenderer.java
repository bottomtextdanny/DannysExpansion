package net.bottomtextdanny.danny_expannny.rendering.entity.living;

import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.danny_expannny.vertex_models.living_entities.ghouls.PetrifiedGhoulModel;
import net.bottomtextdanny.danny_expannny.objects.entities.mob.ghouls.PetrifiedGhoul;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class PetrifiedGhoulRenderer extends MobRenderer<PetrifiedGhoul, PetrifiedGhoulModel> {
    public static final ResourceLocation TEXTURES = new ResourceLocation(DannysExpansion.ID, "textures/entity/ghoul/petrified_ghoul.png");

    public PetrifiedGhoulRenderer(Object manager) {
        this((EntityRendererProvider.Context) manager);
    }

    public PetrifiedGhoulRenderer(EntityRendererProvider.Context manager) {
        super(manager, new PetrifiedGhoulModel(), 0.4F);
    }

    public ResourceLocation getTextureLocation(PetrifiedGhoul entity) {
        return TEXTURES;
    }
}

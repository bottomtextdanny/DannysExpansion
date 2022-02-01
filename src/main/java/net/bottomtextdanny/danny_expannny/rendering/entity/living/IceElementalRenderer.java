package net.bottomtextdanny.danny_expannny.rendering.entity.living;

import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.danny_expannny.vertex_models.living_entities.IceElementalModel;
import net.bottomtextdanny.danny_expannny.objects.entities.mob.ice_elemental.IceElemental;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class IceElementalRenderer extends MobRenderer<IceElemental, IceElementalModel> {
    public static final ResourceLocation TEXTURES = new ResourceLocation(DannysExpansion.ID, "textures/entity/elemental/ice_elemental.png");

    public IceElementalRenderer(Object manager) {
        this((EntityRendererProvider.Context) manager);
    }

    public IceElementalRenderer(EntityRendererProvider.Context manager) {
        super(manager, new IceElementalModel(), 0.0F);
    }

    public ResourceLocation getTextureLocation(IceElemental entity) {
        return TEXTURES;
    }
}

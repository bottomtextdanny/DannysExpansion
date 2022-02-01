package net.bottomtextdanny.danny_expannny.rendering.entity.living;

import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.danny_expannny.objects.entities.mob.ice_elemental.IceElemental;
import net.bottomtextdanny.danny_expannny.objects.entities.mob.varado.Varado;
import net.bottomtextdanny.danny_expannny.vertex_models.living_entities.IceElementalModel;
import net.bottomtextdanny.danny_expannny.vertex_models.living_entities.VaradoModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class VaradoRenderer extends MobRenderer<Varado, VaradoModel> {
    public static final ResourceLocation TEXTURES = new ResourceLocation(DannysExpansion.ID, "textures/entity/varado.png");

    public VaradoRenderer(Object manager) {
        this((EntityRendererProvider.Context) manager);
    }

    public VaradoRenderer(EntityRendererProvider.Context manager) {
        super(manager, new VaradoModel(), 0.0F);
    }

    public ResourceLocation getTextureLocation(Varado entity) {
        return TEXTURES;
    }
}
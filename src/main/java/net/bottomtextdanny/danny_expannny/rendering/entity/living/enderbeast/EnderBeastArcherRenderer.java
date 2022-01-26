package net.bottomtextdanny.danny_expannny.rendering.entity.living.enderbeast;

import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.danny_expannny.vertex_models.living_entities.ender_beasts.EnderBeastArcherModel;
import net.bottomtextdanny.danny_expannny.objects.entities.mob.ender_beast.EnderBeastArcherEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class EnderBeastArcherRenderer extends MobRenderer<EnderBeastArcherEntity, EnderBeastArcherModel> {
    public static final ResourceLocation TEXTURES = new ResourceLocation(DannysExpansion.ID, "textures/entity/ender_beast/ender_beast_archer.png");

    public EnderBeastArcherRenderer(Object manager) {
        this((EntityRendererProvider.Context) manager);
    }

    public EnderBeastArcherRenderer(EntityRendererProvider.Context manager) {
        super(manager, new EnderBeastArcherModel(), 1.5F);
    }

    public ResourceLocation getTextureLocation(EnderBeastArcherEntity entity) {
        return TEXTURES;
    }
}

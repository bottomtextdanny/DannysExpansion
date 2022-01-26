package net.bottomtextdanny.danny_expannny.rendering.entity.living.rammer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.danny_expannny.vertex_models.living_entities.rammers.GrandRammerModel;
import net.bottomtextdanny.danny_expannny.rendering.entity.layer.rammer.GrandRammerEyesLayer;
import net.bottomtextdanny.danny_expannny.rendering.entity.layer.rammer.GrandRammerSaddleLayer;
import net.bottomtextdanny.danny_expannny.objects.entities.animal.rammer.GrandRammerEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class GrandRammerRenderer extends MobRenderer<GrandRammerEntity, GrandRammerModel> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(DannysExpansion.ID, "textures/entity/rammer/grand_rammer.png");

    public ResourceLocation getTextureLocation(GrandRammerEntity entity) {
        return TEXTURE;
    }

    public GrandRammerRenderer(Object manager) {
        this((EntityRendererProvider.Context) manager);
    }

    public GrandRammerRenderer(EntityRendererProvider.Context manager) {
        super(manager, new GrandRammerModel(0), 0.7F);
        this.addLayer(new GrandRammerEyesLayer(this));
        this.addLayer(new GrandRammerSaddleLayer(this));
    }

    protected void scale(GrandRammerEntity entitylivingbaseIn, PoseStack matrixStackIn, float partialTickTime) {
        matrixStackIn.scale(entitylivingbaseIn.getSize() * 1.2F, entitylivingbaseIn.getSize() * 1.2F, entitylivingbaseIn.getSize() * 1.2F);
    }
}

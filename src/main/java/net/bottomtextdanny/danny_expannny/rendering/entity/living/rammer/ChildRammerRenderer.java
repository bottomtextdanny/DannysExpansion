package net.bottomtextdanny.danny_expannny.rendering.entity.living.rammer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.danny_expannny.vertex_models.living_entities.rammers.ChildRammerModel;
import net.bottomtextdanny.danny_expannny.rendering.entity.layer.rammer.ChildRammerEyesLayer;
import net.bottomtextdanny.danny_expannny.objects.entities.animal.rammer.ChildRammerEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class ChildRammerRenderer extends MobRenderer<ChildRammerEntity, ChildRammerModel<ChildRammerEntity>> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(DannysExpansion.ID, "textures/entity/rammer/child_rammer.png");

    public ResourceLocation getTextureLocation(ChildRammerEntity entity) {
        return TEXTURE;
    }

    public ChildRammerRenderer(Object manager) {
        this((EntityRendererProvider.Context) manager);
    }

    public ChildRammerRenderer(EntityRendererProvider.Context manager) {
        super(manager, new ChildRammerModel<>(), 0.7F);
        this.addLayer(new ChildRammerEyesLayer<>(this));
    }

    protected void scale(ChildRammerEntity entitylivingbaseIn, PoseStack matrixStackIn, float partialTickTime) {
        matrixStackIn.scale(entitylivingbaseIn.getSize() * 1.2F, entitylivingbaseIn.getSize() * 1.2F, entitylivingbaseIn.getSize() * 1.2F);
    }
}

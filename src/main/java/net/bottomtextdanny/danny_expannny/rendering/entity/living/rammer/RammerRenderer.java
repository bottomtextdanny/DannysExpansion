package net.bottomtextdanny.danny_expannny.rendering.entity.living.rammer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.danny_expannny.vertex_models.living_entities.rammers.RammerModel;
import net.bottomtextdanny.danny_expannny.rendering.entity.layer.rammer.RammerEyesLayer;
import net.bottomtextdanny.danny_expannny.rendering.entity.layer.rammer.RammerItemHeldLayer;
import net.bottomtextdanny.danny_expannny.objects.entities.animal.rammer.RammerEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

import java.util.Random;

public class RammerRenderer extends MobRenderer<RammerEntity, RammerModel> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(DannysExpansion.ID, "textures/entity/rammer/rammer.png");
    private final Random rnd = new Random();

    public RammerRenderer(Object manager) {
        this((EntityRendererProvider.Context) manager);
    }

    public RammerRenderer(EntityRendererProvider.Context manager) {
        super(manager, new RammerModel(), 0.7F);
        this.addLayer(new RammerEyesLayer(this));
        this.addLayer(new RammerItemHeldLayer(this));
    }

    protected void scale(RammerEntity entitylivingbaseIn, PoseStack matrixStackIn, float partialTickTime) {
        matrixStackIn.scale(entitylivingbaseIn.getSize() * 1.2F, entitylivingbaseIn.getSize() * 1.2F, entitylivingbaseIn.getSize() * 1.2F);
    }

    public Vec3 getRenderOffset(RammerEntity entityIn, float partialTicks) {
        if (entityIn.is_transforming.get()) {
            return new Vec3(this.rnd.nextGaussian() * 0.02D, 0.0D, this.rnd.nextGaussian() * 0.02D);
        } else {
            return super.getRenderOffset(entityIn, partialTicks);
        }
    }

    public ResourceLocation getTextureLocation(RammerEntity entity) {
        return TEXTURE;
    }
}

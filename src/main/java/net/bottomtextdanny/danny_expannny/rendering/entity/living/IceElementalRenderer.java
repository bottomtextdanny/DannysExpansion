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

//    @Override
//    public void render(IceElementalEntity entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
//
//        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
//    }

//    @Override
//    public boolean shouldRender(IceElementalEntity livingEntityIn, Frustum camera, double camX, double camY, double camZ) {
////        if (DE.clientManager.frustum.isSphereInFrustum((float)livingEntityIn.getX(), (float)livingEntityIn.getY(), (float)livingEntityIn.getZ(), 6.0F)) {
////	        DE.clientManager.shaderManager.lightingWorkflow.addLight(new PointLight(EntityUtil.easedPos(livingEntityIn, DEUtil.PARTIAL_TICK), new Vec3(0.0, 0.4, 1.0), 6.0F, 0.78F, 1.8F));
////        }
//
//	    return super.shouldRender(livingEntityIn, camera, camX, camY, camZ);
//    }
}

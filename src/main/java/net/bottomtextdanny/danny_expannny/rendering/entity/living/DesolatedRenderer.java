package net.bottomtextdanny.danny_expannny.rendering.entity.living;

import com.mojang.blaze3d.vertex.PoseStack;
import net.bottomtextdanny.braincell.Braincell;
import net.bottomtextdanny.braincell.mod.opengl_front.point_lighting.SimplePointLight;
import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.danny_expannny.vertex_models.living_entities.DesolatedModel;
import net.bottomtextdanny.dannys_expansion.common.Entities.living.DesolatedEntity;
import net.bottomtextdanny.dannys_expansion.core.Util.qol.DEUtil;
import net.bottomtextdanny.dannys_expansion.core.Util.qol.EntityUtil;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class DesolatedRenderer extends MobRenderer<DesolatedEntity, DesolatedModel> {
    public static final ResourceLocation TEXTURES = new ResourceLocation(DannysExpansion.ID, "textures/entity/desolated.png");

    public DesolatedRenderer(Object manager) {
        this((EntityRendererProvider.Context) manager);
    }

    public DesolatedRenderer(EntityRendererProvider.Context manager) {
        super(manager, new DesolatedModel(), 0.4F);
    }

    @Override
    public void render(DesolatedEntity entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
        
    }

    @Override
    public boolean shouldRender(DesolatedEntity livingEntityIn, Frustum camera, double camX, double camY, double camZ) {
        if (camera.isVisible(new AABB(livingEntityIn.position().add(-5, -5, -5), livingEntityIn.position().add(5, 5, 5)))) {
            Braincell.client().getPostprocessingHandler().getLightingWorkflow().addLight(new SimplePointLight(EntityUtil.easedPos(livingEntityIn, DEUtil.PARTIAL_TICK), new Vec3(0, 0, 1), 20, 1.5F, 2.2F));
        }
        return true;
    }

    public ResourceLocation getTextureLocation(DesolatedEntity entity) {
        return TEXTURES;
    }

}

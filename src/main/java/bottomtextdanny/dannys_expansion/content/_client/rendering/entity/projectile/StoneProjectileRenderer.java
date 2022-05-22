package bottomtextdanny.dannys_expansion.content._client.rendering.entity.projectile;

import bottomtextdanny.dannys_expansion.DannysExpansion;
import bottomtextdanny.dannys_expansion.content._client.model.entities.projectiles.StoneProjectileModel;
import bottomtextdanny.dannys_expansion.content.entities.mob.goblin.StoneProjectile;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class StoneProjectileRenderer extends EntityRenderer<StoneProjectile> {
    public static final ResourceLocation TEXTURES = new ResourceLocation(DannysExpansion.ID, "textures/entity/stone_projectile.png");
    private final StoneProjectileModel model = new StoneProjectileModel();

    public StoneProjectileRenderer(Object manager) {
        this((EntityRendererProvider.Context) manager);
    }

    public StoneProjectileRenderer(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn);
    }

    public void render(StoneProjectile entityIn, float entityYaw, float tickOffset, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
        matrixStackIn.pushPose();
        this.model.setupAnim(entityIn,
                0.0F, 0.0F,
                entityIn.tickCount + tickOffset,
                entityIn.getYRot(), entityIn.getXRot());
        VertexConsumer ivertexbuilder = bufferIn.getBuffer(RenderType.entityTranslucent(this.getTextureLocation(entityIn)));
        this.model.renderToBuffer(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        matrixStackIn.popPose();
        super.render(entityIn, entityYaw, tickOffset, matrixStackIn, bufferIn, packedLightIn);
    }

    public ResourceLocation getTextureLocation(StoneProjectile entity) {
        return TEXTURES;
    }
}

package net.bottomtextdanny.danny_expannny.rendering.entity.spell.bullet;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.VertexHelper;
import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.danny_expannny.objects.entities.projectile.bullet.AbstractBulletEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

public class BulletRenderer<T extends AbstractBulletEntity> extends EntityRenderer<T> {

    public BulletRenderer(Object manager) {
        this((EntityRendererProvider.Context) manager);
    }

    public BulletRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager);
    }

    @Override
    public void render(T entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
        matrixStackIn.pushPose();
        float f1 = Mth.rotLerp(partialTicks, entityIn.yRotO, entityIn.getYRot());
        float f2 = Mth.rotLerp(partialTicks, entityIn.xRotO, entityIn.getXRot());
        float texPos = 0.0625F;
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(-f1));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(f2));


        matrixStackIn.scale(-1.0F, -1.0F, 1.0F);
        VertexConsumer ivertexbuilder = bufferIn.getBuffer(RenderType.dragonExplosionAlpha(getTextureLocation(entityIn)));
        VertexHelper polygonHelper = new VertexHelper(ivertexbuilder, matrixStackIn, packedLightIn);

        float alpha = 0.2F;
        polygonHelper.addQuadPair(
                new Vec3(-texPos / 2, -texPos / 2, texPos / 2 * -10),
                new Vec3(-texPos / 2, -texPos / 2, texPos / 2 * 10),
                new Vec3(texPos / 2, -texPos / 2, texPos / 2 * 10),
                new Vec3(texPos / 2, -texPos / 2, texPos / 2 * -10),
                0, 0, 1, 1, new Quaternion(1, 1, 1, alpha), 15728880);

        polygonHelper.addQuadPair(
                new Vec3(-texPos / 2, texPos / 2, texPos / 2 * -10),
                new Vec3(-texPos / 2, texPos / 2, texPos / 2 * 10),
                new Vec3(texPos / 2, texPos / 2, texPos / 2 * 10),
                new Vec3(texPos / 2, texPos / 2, texPos / 2 * -10),
                0, 0, 1, 1, new Quaternion(1, 1, 1, alpha), 15728880);

        polygonHelper.addQuadPair(
                new Vec3(texPos / 2, -texPos / 2, texPos / 2 * -10),
                new Vec3(texPos / 2, -texPos / 2, texPos / 2 * 10),
                new Vec3(texPos / 2, texPos / 2, texPos / 2 * 10),
                new Vec3(texPos / 2, texPos / 2, texPos / 2 * -10),
                0, 0, 1, 1, new Quaternion(1, 1, 1, alpha), 15728880);

        polygonHelper.addQuadPair(
                new Vec3(-texPos / 2, -texPos / 2, texPos / 2 * -10),
                new Vec3(-texPos / 2, -texPos / 2, texPos / 2 * 10),
                new Vec3(-texPos / 2, texPos / 2, texPos / 2 * 10),
                new Vec3(-texPos / 2, texPos / 2, texPos / 2 * -10),
                0, 0, 1, 1, new Quaternion(1, 1, 1, alpha), 15728880);

        polygonHelper.addQuadPair(
                new Vec3(-texPos / 2, texPos / 2, texPos / 2 * 10),
                new Vec3(-texPos / 2, -texPos / 2, texPos / 2 * 10),
                new Vec3(texPos / 2, -texPos / 2, texPos / 2 * 10),
                new Vec3(texPos / 2, texPos / 2, texPos / 2 * 10),
                0, 0, 0, 0, new Quaternion(1, 1, 1, alpha), 15728880);

        polygonHelper.addQuadPair(
                new Vec3(-texPos / 2, texPos / 2, texPos / 2 * -10),
                new Vec3(-texPos / 2, -texPos / 2, texPos / 2 * -10),
                new Vec3(texPos / 2, -texPos / 2, texPos / 2 * -10),
                new Vec3(texPos / 2, texPos / 2, texPos / 2 * -10),
                0, texPos * 15, 1, 1, new Quaternion(1, 1, 1, alpha), 15728880);

        float easedDifference = Mth.lerp(partialTicks, entityIn.prevDifference, entityIn.difference);
        matrixStackIn.scale(1, 1, 1 + easedDifference);
        matrixStackIn.popPose();
    }

    @Override
    public boolean shouldRender(T livingEntityIn, Frustum camera, double camX, double camY, double camZ) {
        return true;
    }

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        return new ResourceLocation(DannysExpansion.ID, "textures/entity/bullet/bullet.png");
    }

}

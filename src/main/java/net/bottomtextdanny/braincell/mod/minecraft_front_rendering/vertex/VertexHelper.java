package net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Quaternion;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.phys.Vec3;

public final class VertexHelper {
    public final VertexConsumer vertexBuilder;
    public final PoseStack poseStack;
    public int generalLight;

    public VertexHelper(VertexConsumer vertexBuilder, PoseStack poseStack, int light) {
        this.vertexBuilder = vertexBuilder;
        this.poseStack = poseStack;
        this.generalLight = light;
    }

    public void addVertex(float vertexPosX, float vertexPosY, float vertexPosZ, float textureOffsetX, float textureOffsetY) {
        this.vertexBuilder.vertex(getMatrix(), vertexPosX, vertexPosY, vertexPosZ).color(255, 255, 255, 255).uv(textureOffsetX, textureOffsetY).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(this.generalLight).normal(getNormal(), 0.0F, 1.0F, 0.0F).endVertex();
    }

    public void addVertex(float vertexPosX, float vertexPosY, float vertexPosZ, float textureOffsetX, float textureOffsetY, Quaternion color, int light) {
        this.vertexBuilder.vertex(getMatrix(), vertexPosX, vertexPosY, vertexPosZ).color(color.i(), color.j(), color.k(), color.r()).uv(textureOffsetX, textureOffsetY).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(getNormal(), 0.0F, 1.0F, 0.0F).endVertex();
    }


    public void addQuad(Vec3 pos1, Vec3 pos2, Vec3 pos3, Vec3 pos4, float texturePosX, float texturePosY, float textureOffsetX, float textureOffsetY, Quaternion color, int light) {
        addVertex((float)pos1.x, (float)pos1.y, (float)pos1.z, texturePosX, textureOffsetY, color, light);
        addVertex((float)pos2.x, (float)pos2.y, (float)pos2.z, texturePosX, texturePosY, color, light);
        addVertex((float)pos3.x, (float)pos3.y, (float)pos3.z, textureOffsetX, texturePosY, color, light);
        addVertex((float)pos4.x, (float)pos4.y, (float)pos4.z, textureOffsetX, textureOffsetY, color, light);
    }

    public void addQuad(Vec3 pos1, Vec3 pos2, Vec3 pos3, Vec3 pos4, float texturePosX, float texturePosY, float textureOffsetX, float textureOffsetY) {
        addQuad(pos1, pos2, pos3, pos4, texturePosX, texturePosY, textureOffsetX, textureOffsetY, new Quaternion(255, 255, 255, 255), this.generalLight);
    }

    public void addQuadPair(Vec3 pos1, Vec3 pos2, Vec3 pos3, Vec3 pos4, float texturePosX, float texturePosY, float textureOffsetX, float textureOffsetY) {
        addQuad(pos1, pos2, pos3, pos4, texturePosX, texturePosY, textureOffsetX, textureOffsetY, new Quaternion(255, 255, 255, 255), this.generalLight);
        addQuad(pos4, pos3, pos2, pos1, texturePosX, texturePosY, textureOffsetX, textureOffsetY, new Quaternion(255, 255, 255, 255), this.generalLight);

    }

    public void addQuadPair(Vec3 pos1, Vec3 pos2, Vec3 pos3, Vec3 pos4, float texturePosX, float texturePosY, float textureOffsetX, float textureOffsetY, Quaternion color, int light) {
        addQuad(pos1, pos2, pos3, pos4, texturePosX, texturePosY, textureOffsetX, textureOffsetY, color, light);
        addQuad(pos4, pos3, pos2, pos1, texturePosX, texturePosY, textureOffsetX, textureOffsetY, color, light);

    }

    public Matrix4f getMatrix() {
        return this.poseStack.last().pose();
    }

    public Matrix3f getNormal() {
        return this.poseStack.last().normal();
    }
}

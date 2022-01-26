package net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import com.mojang.math.Vector4f;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import net.bottomtextdanny.braincell.underlying.util.matrices.RotationMatrix;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.function.BiConsumer;

public final class BCVoxel {
    public float x;
    public float y;
    public float z;
    public float xRot;
    public float yRot;
    public float zRot;
    public boolean mirror;
    public boolean visible = true;
	public BiConsumer<BCVoxel, PoseStack> renderCallback;
    public float defaultAngleX, defaultAngleY, defaultAngleZ, defaultOffsetX, defaultOffsetY, defaultOffsetZ, defaultSizeX, defaultSizeY, defaultSizeZ;
    public int textureOffsetX, textureOffsetY;
    public float texWidth, texHeight;
    public float scaleX, scaleY, scaleZ;
    public int index = -1;
    BCVoxel parent;
    public final ObjectList<ModelBox> cubeList = new ObjectArrayList<>();
    public final ObjectList<BCVoxel> childModels = new ObjectArrayList<>();


    public BCVoxel(BCEntityModel<? extends Entity> model) {
        this.setScale(1.0F, 1.0F, 1.0F);
        this.setTexSize(model.getTexWidth(), model.getTexHeight());
        model.addPart(this);
    }

    public BCVoxel(BCModel model) {
        this.setScale(1.0F, 1.0F, 1.0F);
        this.setTexSize(model.getTexWidth(), model.getTexHeight());
    }
    
    @SuppressWarnings("unchecked")
    public <T extends BCVoxel> T callback(BiConsumer<BCVoxel, PoseStack> cons) {
    	this.renderCallback = cons;
    	return (T)this;
    }

    public void translateRotateWithParents(PoseStack matrixStackIn) {
        if (this.parent != null) {
            this.parent.translateRotateWithParents(matrixStackIn);
        }

        matrixStackIn.translate(this.x / 16.0F, this.y / 16.0F, this.z / 16.0F);
        if (this.zRot != 0.0F) {
            matrixStackIn.mulPose(Vector3f.ZP.rotation(this.zRot));
        }

        if (this.yRot != 0.0F) {
            matrixStackIn.mulPose(Vector3f.YP.rotation(this.yRot));
        }

        if (this.xRot != 0.0F) {
            matrixStackIn.mulPose(Vector3f.XP.rotation(this.xRot));
        }
    }

    public void translateRotateWithParentsInverted(PoseStack matrixStackIn) {
        if (this.parent != null) {
            this.parent.translateRotateWithParentsInverted(matrixStackIn);
        }

        matrixStackIn.translate(this.x / 16.0F, this.y / 16.0F, this.z / 16.0F);
        if (this.zRot != 0.0F) {
            matrixStackIn.mulPose(Vector3f.ZP.rotation(this.zRot));
        }

        if (this.yRot != 0.0F) {
            matrixStackIn.mulPose(Vector3f.YP.rotation(-this.yRot));
        }

        if (this.xRot != 0.0F) {
            matrixStackIn.mulPose(Vector3f.XP.rotation(-this.xRot));
        }
    }

    public Vec3 getAbsolutePosition(Vec3 holder, float partialTicks, LivingEntity entity) {
        Vec3 modelPosition = modelPositionWithParentsInverted(holder);
        double entityPositionX = Mth.lerp(partialTicks, entity.xOld, entity.getX());
        double entityPositionY = Mth.lerp(partialTicks, entity.yOld, entity.getY());
        double entityPositionZ = Mth.lerp(partialTicks, entity.zOld, entity.getZ());

        Vec3 entityPos = new Vec3(entityPositionX, entityPositionY, entityPositionZ);

        RotationMatrix matrixRotY = new RotationMatrix();

        matrixRotY.rotY((float)Math.toRadians(-entity.yBodyRot));

        modelPosition = matrixRotY.getTransform(modelPosition);

        modelPosition = modelPosition.add(0.0, 1.5, 0.0);

        return  entityPos.add(modelPosition);
    }

    public Vec3 modelPositionWithParentsInverted(Vec3 holder) {
        Vec3 rendererPos = new Vec3(holder.x(), holder.y(), holder.z());
        Vec3 translation = new Vec3(this.x / 16, -this.y / 16, -this.z / 16);
        RotationMatrix matrixRotX = new RotationMatrix();
        RotationMatrix matrixRotY = new RotationMatrix();
        RotationMatrix matrixRotZ = new RotationMatrix();

        matrixRotX.rotX(this.xRot);
        matrixRotY.rotY(-this.yRot);
        matrixRotZ.rotZ(-this.zRot);

        rendererPos = matrixRotZ.getTransform(rendererPos);
        rendererPos = matrixRotX.getTransform(rendererPos);
        rendererPos = matrixRotY.getTransform(rendererPos);
        rendererPos = rendererPos.add(translation);
        if (this.parent != null) {
            return this.parent.modelPositionWithParentsInverted(rendererPos);
        }

        return new Vec3(rendererPos.x(), rendererPos.y(), rendererPos.z());
    }

    public void addChild(BCVoxel renderer) {
        this.childModels.add(renderer);
        renderer.parent = this;
    }

    public void setPos(float rotationPointXIn, float rotationPointYIn, float rotationPointZIn) {
        this.x = rotationPointXIn;
        this.y = rotationPointYIn;
        this.z = rotationPointZIn;
    }

    public void setRotationAngle(float rotationAngleXIn, float rotationAngleYIn, float rotationAngleZIn) {
        this.xRot = rotationAngleXIn;
        this.yRot = rotationAngleYIn;
        this.zRot = rotationAngleZIn;
    }

    public void setScale(float x, float y, float z) {
        this.scaleX = x;
        this.scaleY = y;
        this.scaleZ = z;
    }

    public void getDefaultState() {
        this.defaultAngleX = this.xRot;
        this.defaultAngleY = this.yRot;
        this.defaultAngleZ = this.zRot;

        this.defaultOffsetX = this.x;
        this.defaultOffsetY = this.y;
        this.defaultOffsetZ = this.z;

        this.defaultSizeX = this.scaleX;
        this.defaultSizeY = this.scaleY;
        this.defaultSizeZ = this.scaleZ;
    }

    public void setDefaultState() {
        this.xRot = this.defaultAngleX;
        this.yRot = this.defaultAngleY;
        this.zRot = this.defaultAngleZ;

        this.x = this.defaultOffsetX;
        this.y = this.defaultOffsetY;
        this.z = this.defaultOffsetZ;

        setScale(this.defaultSizeX, this.defaultSizeY, this.defaultSizeZ);
    }

  
    public BCVoxel texOffs(int x, int y) {
        this.textureOffsetX = x;
        this.textureOffsetY = y;
        return this;
    }

  
    public BCVoxel setTexSize(int textureWidthIn, int textureHeightIn) {
        this.texWidth = (float)textureWidthIn;
        this.texHeight = (float)textureHeightIn;
        return this;
    }

    
    public BCVoxel addBox(String partName, float x, float y, float z, int width, int height, int depth, float delta, int texX, int texY) {
        this.texOffs(texX, texY);
        this.addBox(this.textureOffsetX, this.textureOffsetY, x, y, z, (float)width, (float)height, (float)depth, delta, delta, delta, this.mirror, false);
        return this;
    }

    public BCVoxel addBox(float x, float y, float z, float width, float height, float depth) {
        this.addBox(this.textureOffsetX, this.textureOffsetY, x, y, z, width, height, depth, 0.0F, 0.0F, 0.0F, this.mirror, false);
        return this;
    }

    public BCVoxel addBox(float x, float y, float z, float width, float height, float depth, boolean mirrorIn) {
        this.addBox(this.textureOffsetX, this.textureOffsetY, x, y, z, width, height, depth, 0.0F, 0.0F, 0.0F, mirrorIn, false);
        return this;
    }

    public void addBox(float x, float y, float z, float width, float height, float depth, float delta) {
        this.addBox(this.textureOffsetX, this.textureOffsetY, x, y, z, width, height, depth, delta, delta, delta, this.mirror, false);
    }

    public void addBox(float x, float y, float z, float width, float height, float depth, float deltaX, float deltaY, float deltaZ) {
        this.addBox(this.textureOffsetX, this.textureOffsetY, x, y, z, width, height, depth, deltaX, deltaY, deltaZ, this.mirror, false);
    }

    public void addBox(float x, float y, float z, float width, float height, float depth, float delta, boolean mirrorIn) {
        this.addBox(this.textureOffsetX, this.textureOffsetY, x, y, z, width, height, depth, delta, delta, delta, mirrorIn, false);
    }

    private void addBox(int texOffX, int texOffY, float x, float y, float z, float width, float height, float depth, float deltaX, float deltaY, float deltaZ, boolean mirorIn, boolean p_228305_13_) {
        this.cubeList.add(new ModelBox(texOffX, texOffY, x, y, z, width, height, depth, deltaX, deltaY, deltaZ, mirorIn, this.texWidth, this.texHeight));
    }

    public void render(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn) {
        this.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
    }

    public void render(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        if (this.visible) {
	        matrixStackIn.pushPose();
	        this.translateRotate(matrixStackIn);
	        matrixStackIn.scale(this.scaleX, this.scaleY, this.scaleZ);
	        if (this.renderCallback != null) {
                this.renderCallback.accept(this, matrixStackIn);
	        }
            if (!this.cubeList.isEmpty() || !this.childModels.isEmpty()) {
                this.doRender(matrixStackIn.last(), bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);

                for (BCVoxel modelrenderer : this.childModels) {
                    modelrenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                }
            }
	        matrixStackIn.popPose();
        }
    }

    public void translateRotate(PoseStack matrixStackIn) {
        matrixStackIn.translate(this.x / 16.0F, this.y / 16.0F, this.z / 16.0F);
        if (this.zRot != 0.0F) {
            matrixStackIn.mulPose(Vector3f.ZP.rotation(this.zRot));
        }

        if (this.yRot != 0.0F) {
            matrixStackIn.mulPose(Vector3f.YP.rotation(this.yRot));
        }

        if (this.xRot != 0.0F) {
            matrixStackIn.mulPose(Vector3f.XP.rotation(this.xRot));
        }

    }

    private void doRender(PoseStack.Pose matrixEntryIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        Matrix4f matrix4f = matrixEntryIn.pose();
        Matrix3f matrix3f = matrixEntryIn.normal();

        for (ModelBox modelrenderer$modelbox : this.cubeList) {
            for (TexturedQuad modelrenderer$texturedquad : modelrenderer$modelbox.quads) {
                Vector3f vector3f = modelrenderer$texturedquad.normal.copy();
                vector3f.transform(matrix3f);
                float f = vector3f.x();
                float f1 = vector3f.y();
                float f2 = vector3f.z();

                for(int i = 0; i < 4; ++i) {
                    BCVoxel.PositionTextureVertex MoreContentModelRenderer$positiontexturevertex = modelrenderer$texturedquad.vertexPositions[i];
                    float f3 = MoreContentModelRenderer$positiontexturevertex.position.x() / 16.0F;
                    float f4 = MoreContentModelRenderer$positiontexturevertex.position.y() / 16.0F;
                    float f5 = MoreContentModelRenderer$positiontexturevertex.position.z() / 16.0F;
                    Vector4f vector4f = new Vector4f(f3, f4, f5, 1.0F);
                    vector4f.transform(matrix4f);
                    bufferIn.vertex(vector4f.x(), vector4f.y(), vector4f.z(), red, green, blue, alpha, MoreContentModelRenderer$positiontexturevertex.textureU, MoreContentModelRenderer$positiontexturevertex.textureV, packedOverlayIn, packedLightIn, f, f1, f2);
                }
            }
        }

    }

    @OnlyIn(Dist.CLIENT)
    static class PositionTextureVertex {
        public final Vector3f position;
        public final float textureU;
        public final float textureV;

        public PositionTextureVertex(float x, float y, float z, float texU, float texV) {
            this(new Vector3f(x, y, z), texU, texV);
        }

        public BCVoxel.PositionTextureVertex setTextureUV(float texU, float texV) {
            return new BCVoxel.PositionTextureVertex(this.position, texU, texV);
        }

        public PositionTextureVertex(Vector3f posIn, float texU, float texV) {
            this.position = posIn;
            this.textureU = texU;
            this.textureV = texV;
        }
    }

    @OnlyIn(Dist.CLIENT)
    static class TexturedQuad {
        public final BCVoxel.PositionTextureVertex[] vertexPositions;
        public final Vector3f normal;

        public TexturedQuad(BCVoxel.PositionTextureVertex[] positionsIn, float u1, float v1, float u2, float v2, float texWidth, float texHeight, boolean mirrorIn, Direction directionIn) {
            this.vertexPositions = positionsIn;
            float f = 0.0F / texWidth;
            float f1 = 0.0F / texHeight;
            positionsIn[0] = positionsIn[0].setTextureUV(u2 / texWidth - f, v1 / texHeight + f1);
            positionsIn[1] = positionsIn[1].setTextureUV(u1 / texWidth + f, v1 / texHeight + f1);
            positionsIn[2] = positionsIn[2].setTextureUV(u1 / texWidth + f, v2 / texHeight - f1);
            positionsIn[3] = positionsIn[3].setTextureUV(u2 / texWidth - f, v2 / texHeight - f1);
            if (mirrorIn) {
                int i = positionsIn.length;

                for(int j = 0; j < i / 2; ++j) {
                    BCVoxel.PositionTextureVertex MoreContentModelRenderer$positiontexturevertex = positionsIn[j];
                    positionsIn[j] = positionsIn[i - 1 - j];
                    positionsIn[i - 1 - j] = MoreContentModelRenderer$positiontexturevertex;
                }
            }

            this.normal = directionIn.step();
            if (mirrorIn) {
                this.normal.mul(-1.0F, 1.0F, 1.0F);
            }

        }
    }

    public static class ModelBox {
        protected final TexturedQuad[] quads;
        public float posX1;
        public float posY1;
        public float posZ1;
        public float posX2;
        public float posY2;
        public float posZ2;

        public ModelBox(int texOffX, int texOffY, float x, float y, float z, float width, float height, float depth, float deltaX, float deltaY, float deltaZ, boolean mirorIn, float texWidth, float texHeight) {
            this.posX1 = x;
            this.posY1 = y;
            this.posZ1 = z;
            this.posX2 = x + width;
            this.posY2 = y + height;
            this.posZ2 = z + depth;
            this.quads = new TexturedQuad[6];
            float f = x + width;
            float f1 = y + height;
            float f2 = z + depth;
            x = x - deltaX;
            y = y - deltaY;
            z = z - deltaZ;
            f = f + deltaX;
            f1 = f1 + deltaY;
            f2 = f2 + deltaZ;
            if(mirorIn) {
                float f3 = f;
                f = x;
                x = f3;
            }

            PositionTextureVertex modelrenderer$positiontexturevertex7 = new PositionTextureVertex(x, y, z, 0.0F, 0.0F);
            PositionTextureVertex modelrenderer$positiontexturevertex = new PositionTextureVertex(f, y, z, 0.0F, 8.0F);
            PositionTextureVertex modelrenderer$positiontexturevertex1 = new PositionTextureVertex(f, f1, z, 8.0F, 8.0F);
            PositionTextureVertex modelrenderer$positiontexturevertex2 = new PositionTextureVertex(x, f1, z, 8.0F, 0.0F);
            PositionTextureVertex modelrenderer$positiontexturevertex3 = new PositionTextureVertex(x, y, f2, 0.0F, 0.0F);
            PositionTextureVertex modelrenderer$positiontexturevertex4 = new PositionTextureVertex(f, y, f2, 0.0F, 8.0F);
            PositionTextureVertex modelrenderer$positiontexturevertex5 = new PositionTextureVertex(f, f1, f2, 8.0F, 8.0F);
            PositionTextureVertex modelrenderer$positiontexturevertex6 = new PositionTextureVertex(x, f1, f2, 8.0F, 0.0F);
            float f4 = (float)texOffX;
            float f5 = (float)texOffX + depth;
            float f6 = (float)texOffX + depth + width;
            float f7 = (float)texOffX + depth + width + width;
            float f8 = (float)texOffX + depth + width + depth;
            float f9 = (float)texOffX + depth + width + depth + width;
            float f10 = (float)texOffY;
            float f11 = (float)texOffY + depth;
            float f12 = (float)texOffY + depth + height;
            this.quads[2] = new TexturedQuad(new PositionTextureVertex[]{modelrenderer$positiontexturevertex4, modelrenderer$positiontexturevertex3, modelrenderer$positiontexturevertex7, modelrenderer$positiontexturevertex}, f5, f10, f6, f11, texWidth, texHeight, mirorIn, Direction.DOWN);
            this.quads[3] = new TexturedQuad(new PositionTextureVertex[]{modelrenderer$positiontexturevertex1, modelrenderer$positiontexturevertex2, modelrenderer$positiontexturevertex6, modelrenderer$positiontexturevertex5}, f6, f11, f7, f10, texWidth, texHeight, mirorIn, Direction.UP);
            this.quads[1] = new TexturedQuad(new PositionTextureVertex[]{modelrenderer$positiontexturevertex7, modelrenderer$positiontexturevertex3, modelrenderer$positiontexturevertex6, modelrenderer$positiontexturevertex2}, f4, f11, f5, f12, texWidth, texHeight, mirorIn, Direction.WEST);
            this.quads[4] = new TexturedQuad(new PositionTextureVertex[]{modelrenderer$positiontexturevertex, modelrenderer$positiontexturevertex7, modelrenderer$positiontexturevertex2, modelrenderer$positiontexturevertex1}, f5, f11, f6, f12, texWidth, texHeight, mirorIn, Direction.NORTH);
            this.quads[0] = new TexturedQuad(new PositionTextureVertex[]{modelrenderer$positiontexturevertex4, modelrenderer$positiontexturevertex, modelrenderer$positiontexturevertex1, modelrenderer$positiontexturevertex5}, f6, f11, f8, f12, texWidth, texHeight, mirorIn, Direction.EAST);
            this.quads[5] = new TexturedQuad(new PositionTextureVertex[]{modelrenderer$positiontexturevertex3, modelrenderer$positiontexturevertex4, modelrenderer$positiontexturevertex5, modelrenderer$positiontexturevertex6}, f8, f11, f9, f12, texWidth, texHeight, mirorIn, Direction.SOUTH);
        }
    }
}

package net.bottomtextdanny.danny_expannny.vertex_models.living_entities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCEntityModel;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCVoxel;
import net.bottomtextdanny.dannys_expansion.common.Entities.living.JemossellyEntity;
import net.bottomtextdanny.dannys_expansion.core.Util.DEMath;
import net.minecraft.util.Mth;

public class JemossellyModel extends BCEntityModel<JemossellyEntity> {
    private final BCVoxel model;
    private final BCVoxel body;
    private final BCVoxel rightFrontTent;
    private final BCVoxel rightBackTent;
    private final BCVoxel leftFrontTent;
    private final BCVoxel leftBackTent;

    public JemossellyModel() {
        this.texWidth = 64;
        this.texHeight = 64;

        this.model = new BCVoxel(this);
        this.model.setPos(0.0F, 24.0F, 0.0F);


        this.body = new BCVoxel(this);
        this.body.setPos(0.0F, -1.0F, 0.0F);
        this.model.addChild(this.body);
        this.body.texOffs(0, 0).addBox(-5.0F, -7.0F, -5.0F, 10.0F, 7.0F, 10.0F, 0.0F, false);
        this.body.texOffs(0, 17).addBox(-7.0F, -13.0F, -7.0F, 14.0F, 6.0F, 14.0F, 0.0F, false);

        this.rightFrontTent = new BCVoxel(this);
        this.rightFrontTent.setPos(-3.0F, 0.0F, -3.0F);
        this.body.addChild(this.rightFrontTent);
        setRotationAngle(this.rightFrontTent, 0.0F, 0.7854F, 0.0F);
        this.rightFrontTent.texOffs(0, 37).addBox(-4.5F, 0.0F, 0.0F, 9.0F, 13.0F, 0.0F, 0.0F, false);

        this.rightBackTent = new BCVoxel(this);
        this.rightBackTent.setPos(-3.0F, 0.0F, 3.0F);
        this.body.addChild(this.rightBackTent);
        setRotationAngle(this.rightBackTent, 0.0F, 2.3562F, 0.0F);
        this.rightBackTent.texOffs(0, 37).addBox(-4.5F, 0.0F, 0.0F, 9.0F, 13.0F, 0.0F, 0.0F, false);

        this.leftFrontTent = new BCVoxel(this);
        this.leftFrontTent.setPos(3.0F, 0.0F, -3.0F);
        this.body.addChild(this.leftFrontTent);
        setRotationAngle(this.leftFrontTent, 0.0F, -0.7854F, 0.0F);
        this.leftFrontTent.texOffs(0, 37).addBox(-4.5F, 0.0F, 0.0F, 9.0F, 13.0F, 0.0F, 0.0F, false);

        this.leftBackTent = new BCVoxel(this);
        this.leftBackTent.setPos(4.0F, 0.0F, 3.0F);
        this.body.addChild(this.leftBackTent);
        setRotationAngle(this.leftBackTent, 0.0F, -2.5307F, 0.0F);
        this.leftBackTent.texOffs(0, 37).addBox(-4.5F, 0.0F, 0.0F, 9.0F, 13.0F, 0.0F, 0.0F, false);

        setupDefaultState();
    }

    @Override
    public void handleRotations(JemossellyEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch) {
        final float easedRotYaw = Mth.lerp(net.bottomtextdanny.dannys_expansion.core.Util.qol.DEUtil.PARTIAL_TICK, entity.yRotO, entity.getYRot());
        final float easedRenderDif = Mth.lerp(net.bottomtextdanny.dannys_expansion.core.Util.qol.DEUtil.PARTIAL_TICK, entity.prevRenderYawRot, entity.renderYawRot);
        final float easedMovement = (float) Mth.lerp(net.bottomtextdanny.dannys_expansion.core.Util.qol.DEUtil.PARTIAL_TICK, entity.prevRenderMovement, entity.renderMovement);
        final float fromPast = Mth.degreesDifference(easedRenderDif, easedRotYaw) - 90.0F;
        final float fPX = DEMath.sin(Math.toRadians(fromPast));
        final float fPZ = DEMath.cos(Math.toRadians(fromPast));

        this.body.yRot = headYaw;
        setRotationAngleDegrees(this.body, easedMovement * 20 * fPX, 0, easedMovement * 20 * fPZ);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        this.model.render(poseStack, buffer, packedLight, packedOverlay);
    }
}

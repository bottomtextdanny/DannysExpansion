package net.bottomtextdanny.danny_expannny.vertex_models.misc;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.bottomtextdanny.braincell.mod.client_animation.EntityModelAnimator;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCEntityModel;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCVoxel;
import net.bottomtextdanny.danny_expannny.objects.entities.TestDummyEntity;
import net.bottomtextdanny.dannys_expansion.core.Util.DEMath;
import net.bottomtextdanny.dannys_expansion.core.Util.animation.Easing;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

public class TestDummyModel extends BCEntityModel<TestDummyEntity> {
    private final BCVoxel model;
    private final BCVoxel stand;
    private final BCVoxel stick;
    private final BCVoxel body;
    private final BCVoxel head;

    public TestDummyModel() {
        this.texWidth = 64;
        this.texHeight = 64;

        this.model = new BCVoxel(this);
        this.model.setPos(0.0F, 0.0F, 0.0F);


        this.stand = new BCVoxel(this);
        this.stand.setPos(0.0F, 0.0F, 0.0F);
        this.model.addChild(this.stand);
        this.stand.texOffs(0, 0).addBox(-6.0F, -2.0F, -6.0F, 12.0F, 2.0F, 12.0F, 0.0F, false);

        this.stick = new BCVoxel(this);
        this.stick.setPos(0.0F, -1.0F, 0.0F);
        this.stand.addChild(this.stick);
        this.stick.texOffs(0, 14).addBox(-1.0F, -9.0F, -1.0F, 2.0F, 8.0F, 2.0F, 0.0F, false);

        this.body = new BCVoxel(this);
        this.body.setPos(0.0F, -9.0F, 0.0F);
        this.stick.addChild(this.body);
        setRotationAngle(this.body, 0.0436F, 0.0F, 0.0F);
        this.body.texOffs(0, 24).addBox(-5.0F, -12.0F, -2.5F, 10.0F, 12.0F, 5.0F, 0.0F, false);

        this.head = new BCVoxel(this);
        this.head.setPos(0.0F, -12.0F, 0.0F);
        this.body.addChild(this.head);
        setRotationAngle(this.head, 0.1745F, 0.0F, 0.0F);
        this.head.texOffs(0, 41).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F, false);

        setupDefaultState();
    }

    @Override
    public void handleRotations(TestDummyEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
    }

    @Override
    public void handleKeyframedAnimations(TestDummyEntity entity, float limbSwing, float limbSwingAmount, float headYaw, float headPitch) {
        super.handleKeyframedAnimations(entity, limbSwing, limbSwingAmount, headYaw, headPitch);

        if (entity.mainAnimationModule.isPlaying(entity.hurtAnimation)) {
            EntityModelAnimator animator = new EntityModelAnimator(this, entity.mainAnimationModule.linearProgress());
            Vec3 fromHit = DEMath.fromPitchYaw(0, Mth.degreesDifference(entity.hitYaw, entity.getYRot()) - 90.0F);

            float x = (float) fromHit.x;
            float z = (float) fromHit.z;
            animator.setupKeyframe(3);
            animator.rotate(this.stick, -3.0F * x, 0.0F, -3.0F * z);
            animator.rotate(this.body, -17.5F * x, 0.0F, -17.5F * z);
            animator.rotate(this.head, 7.5F * x, 0.0F, 7.5F * z);
            animator.apply();

            animator.setupKeyframe(4);
            animator.rotate(this.stick, 2.0F * x, 0.0F, 2.0F * z);
            animator.rotate(this.body, 7.5F * x, 0.0F, 7.5F * z);
            animator.rotate(this.head, -7.5F * x, 0.0F, -7.5F * z);
            animator.apply(Easing.EASE_OUT_SQUARE);

            animator.emptyKeyframe(3.0F, Easing.LINEAR);
        }

    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        this.model.render(poseStack, buffer, packedLight, packedOverlay);
    }
}

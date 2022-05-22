package bottomtextdanny.dannys_expansion.content._client.model.entities.misc_entities;

import bottomtextdanny.dannys_expansion.content.entities.misc.TestDummyEntity;
import bottomtextdanny.dannys_expansion._util.DEMath;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import bottomtextdanny.braincell.base.Easing;
import bottomtextdanny.braincell.mod._base.animation.ModelAnimator;
import bottomtextdanny.braincell.mod.rendering.modeling.BCEntityModel;
import bottomtextdanny.braincell.mod._base.rendering.core_modeling.BCJoint;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

public class TestDummyModel extends BCEntityModel<TestDummyEntity> {
    private final BCJoint model;
    private final BCJoint stand;
    private final BCJoint stick;
    private final BCJoint body;
    private final BCJoint head;

    public TestDummyModel() {
        this.texWidth = 64;
        this.texHeight = 64;

        this.model = new BCJoint(this);
        this.model.setPosCore(0.0F, 0.0F, 0.0F);


        this.stand = new BCJoint(this);
        this.stand.setPosCore(0.0F, 0.0F, 0.0F);
        this.model.addChild(this.stand);
        this.stand.uvOffset(0, 0).addBox(-6.0F, -2.0F, -6.0F, 12.0F, 2.0F, 12.0F, 0.0F, false);

        this.stick = new BCJoint(this);
        this.stick.setPosCore(0.0F, -1.0F, 0.0F);
        this.stand.addChild(this.stick);
        this.stick.uvOffset(0, 14).addBox(-1.0F, -9.0F, -1.0F, 2.0F, 8.0F, 2.0F, 0.0F, false);

        this.body = new BCJoint(this);
        this.body.setPosCore(0.0F, -9.0F, 0.0F);
        this.stick.addChild(this.body);
        setRotationAngle(this.body, 0.0436F, 0.0F, 0.0F);
        this.body.uvOffset(0, 24).addBox(-5.0F, -12.0F, -2.5F, 10.0F, 12.0F, 5.0F, 0.0F, false);

        this.head = new BCJoint(this);
        this.head.setPosCore(0.0F, -12.0F, 0.0F);
        this.body.addChild(this.head);
        setRotationAngle(this.head, 0.1745F, 0.0F, 0.0F);
        this.head.uvOffset(0, 41).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F, false);

        //modelInitEnd
    }

    @Override
    public void handleRotations(TestDummyEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
    }

    @Override
    public void handleKeyframedAnimations(TestDummyEntity entity, float limbSwing, float limbSwingAmount, float headYaw, float headPitch) {
        super.handleKeyframedAnimations(entity, limbSwing, limbSwingAmount, headYaw, headPitch);

        if (entity.mainAnimationModule.isPlaying(TestDummyEntity.HURT)) {
            ModelAnimator animator = new ModelAnimator(this, entity.mainAnimationModule.linearProgress());
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

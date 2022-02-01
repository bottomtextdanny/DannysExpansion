package net.bottomtextdanny.danny_expannny.vertex_models.living_entities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.bottomtextdanny.braincell.mod.client_animation.EntityModelAnimator;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCEntityModel;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCVoxel;
import net.bottomtextdanny.dannys_expansion.common.Entities.living.SporerEntity;
import net.bottomtextdanny.dannys_expansion.core.Util.animation.Easing;

public class SporerModel extends BCEntityModel<SporerEntity> {
    private final BCVoxel whole;
    private final BCVoxel body;
    private final BCVoxel bulb;
    private final BCVoxel petal1;
    private final BCVoxel petal2;
    private final BCVoxel petal3;
    private final BCVoxel petal4;
    private final BCVoxel hair;
    private final BCVoxel hair4;
    private final BCVoxel hair3;
    private final BCVoxel hair2;
    private final BCVoxel hair1;
    private final BCVoxel hairLayers;

    public SporerModel() {
        this.texWidth = 128;
        this.texHeight = 128;

        this.whole = new BCVoxel(this);
        this.whole.setPos(0.0F, 24.0F, 0.0F);


        this.body = new BCVoxel(this);
        this.body.setPos(0.0F, 0.0F, 0.0F);
        this.whole.addChild(this.body);
        this.body.texOffs(0, 0).addBox(-10.0F, -12.0F, -10.0F, 20.0F, 12.0F, 20.0F, 0.0F, false);

        this.bulb = new BCVoxel(this);
        this.bulb.setPos(0.0F, -4.0F, 0.0F);
        this.body.addChild(this.bulb);


        this.petal1 = new BCVoxel(this);
        this.petal1.setPos(-4.0F, -6.0F, 0.0F);
        this.bulb.addChild(this.petal1);
        setRotationAngle(this.petal1, 0.0F, 0.0F, 0.3054F);
        this.petal1.texOffs(68, 20).addBox(-6.0F, -11.0F, -6.0F, 5.0F, 11.0F, 12.0F, 0.0F, false);

        this.petal2 = new BCVoxel(this);
        this.petal2.setPos(4.0F, -6.0F, 0.0F);
        this.bulb.addChild(this.petal2);
        setRotationAngle(this.petal2, 0.0F, 3.1416F, -0.3054F);
        this.petal2.texOffs(44, 77).addBox(-6.0F, -11.0F, -6.0F, 5.0F, 11.0F, 12.0F, 0.0F, false);

        this.petal3 = new BCVoxel(this);
        this.petal3.setPos(0.0F, -6.0F, -4.0F);
        this.bulb.addChild(this.petal3);
        setRotationAngle(this.petal3, -0.3054F, 0.0F, 0.0F);
        this.petal3.texOffs(77, 101).addBox(-6.0F, -11.0F, -6.0F, 12.0F, 11.0F, 5.0F, 0.0F, false);

        this.petal4 = new BCVoxel(this);
        this.petal4.setPos(0.0F, -6.0F, 4.0F);
        this.bulb.addChild(this.petal4);
        setRotationAngle(this.petal4, -0.3054F, 3.1416F, 0.0F);
        this.petal4.texOffs(77, 101).addBox(-6.0F, -11.0F, -6.0F, 12.0F, 11.0F, 5.0F, 0.0F, false);

        this.hair = new BCVoxel(this);
        this.hair.setPos(0.0F, 0.0F, 0.0F);
        this.whole.addChild(this.hair);
        this.hair.texOffs(56, 56).addBox(-8.0F, 0.0F, -8.0F, 16.0F, 5.0F, 16.0F, 0.0F, false);

        this.hair4 = new BCVoxel(this);
        this.hair4.setPos(0.0F, 1.0F, 9.0F);
        this.hair.addChild(this.hair4);
        setRotationAngle(this.hair4, -0.3054F, 3.1416F, 0.0F);
        this.hair4.texOffs(0, 0).addBox(-5.0F, 0.0F, 0.0F, 10.0F, 9.0F, 0.0F, 0.0F, false);

        this.hair3 = new BCVoxel(this);
        this.hair3.setPos(0.0F, 1.0F, -9.0F);
        this.hair.addChild(this.hair3);
        setRotationAngle(this.hair3, -0.3054F, 0.0F, 0.0F);
        this.hair3.texOffs(0, 0).addBox(-5.0F, 0.0F, 0.0F, 10.0F, 9.0F, 0.0F, 0.0F, false);

        this.hair2 = new BCVoxel(this);
        this.hair2.setPos(-9.0F, 1.0F, 0.0F);
        this.hair.addChild(this.hair2);
        setRotationAngle(this.hair2, 0.0F, 0.0F, 0.3054F);
        this.hair2.texOffs(60, 0).addBox(0.0F, 0.0F, -5.0F, 0.0F, 9.0F, 10.0F, 0.0F, false);

        this.hair1 = new BCVoxel(this);
        this.hair1.setPos(9.0F, 1.0F, 0.0F);
        this.hair.addChild(this.hair1);
        setRotationAngle(this.hair1, 0.0F, 3.1416F, -0.3054F);
        this.hair1.texOffs(66, 67).addBox(0.0F, 0.0F, -5.0F, 0.0F, 9.0F, 10.0F, 0.0F, false);

        this.hairLayers = new BCVoxel(this);
        this.hairLayers.setPos(0.0F, 6.0F, 0.0F);
        this.hair.addChild(this.hairLayers);
        this.hairLayers.texOffs(0, 32).addBox(-9.0F, -6.0F, -9.0F, 18.0F, 11.0F, 18.0F, 0.0F, false);
        this.hairLayers.texOffs(0, 61).addBox(-7.0F, -1.0F, -7.0F, 14.0F, 11.0F, 14.0F, 0.0F, false);
        this.hairLayers.texOffs(90, 0).addBox(-3.0F, -1.0F, -3.0F, 6.0F, 16.0F, 6.0F, 0.0F, false);

        setupDefaultState();
    }
    
    @Override
    public void handleKeyframedAnimations(SporerEntity entity, float limbSwing, float limbSwingAmount, float headYaw, float headPitch) {
        super.handleKeyframedAnimations(entity, limbSwing, limbSwingAmount, headYaw, headPitch);

        EntityModelAnimator animator = new EntityModelAnimator(this, entity.mainHandler.getTick()  + getPartialTick());

        if(entity.mainHandler.isPlaying(SporerEntity.BOING)) {

            animator.setupKeyframe(6);
            animator.scale(this.whole, 0.6666F, 0.0F, 0.6666F);
            animator.scale(this.body, 0.0F, -0.4F, 0.0F);
            animator.apply();

            animator.setupKeyframe(4);
            animator.scale(this.whole, -0.4F, 0.0F, -0.4F);
            animator.scale(this.body, 0.0F, 0.6666F, 0.0F);
            animator.apply();

            animator.emptyKeyframe(13, Easing.EASE_IN_OUT_BACK);
        }
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        this.whole.render(poseStack, buffer, packedLight, packedOverlay);
    }
}

package net.bottomtextdanny.danny_expannny.vertex_models.spells;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.bottomtextdanny.braincell.mod.client_animation.EntityModelAnimator;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCEntityModel;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCVoxel;
import net.bottomtextdanny.dannys_expansion.common.Entities.spell.FoamshroomProjectileEntity;
import net.bottomtextdanny.dannys_expansion.core.Util.animation.Easing;

public class FoamshroomProjectileModel extends BCEntityModel<FoamshroomProjectileEntity> {
    private final BCVoxel model;
    private final BCVoxel body;
    private final BCVoxel cap;

    public FoamshroomProjectileModel() {
        this.texWidth = 64;
        this.texHeight = 64;

        this.model = new BCVoxel(this);
        this.model.setPos(0.0F, 0.0F, 0.0F);


        this.body = new BCVoxel(this);
        this.body.setPos(0.0F, -4.0F, 0.0F);
        this.model.addChild(this.body);
        this.body.texOffs(0, 0).addBox(-3.0F, 0.0F, -3.0F, 6.0F, 4.0F, 6.0F, 0.0F, false);

        this.cap = new BCVoxel(this);
        this.cap.setPos(0.0F, 0.0F, 0.0F);
        this.body.addChild(this.cap);
        this.cap.texOffs(0, 10).addBox(-5.0F, -5.0F, -5.0F, 10.0F, 5.0F, 10.0F, 0.0F, false);

        setupDefaultState();
    }

    @Override
    public void handleRotations(FoamshroomProjectileEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch) {
        this.body.xRot = (entity.getRenderRotPitch(getPartialTick()) + 90) * RAD;
    }

    @Override
    public void handleKeyframedAnimations(FoamshroomProjectileEntity entity, float limbSwing, float limbSwingAmount, float headYaw, float headPitch) {
    	if (entity.mainHandler.isPlaying(FoamshroomProjectileEntity.EXPLODE)) {
		    EntityModelAnimator animator = new EntityModelAnimator(this, entity.mainHandler.linearProgress());
		    
		    animator.setupKeyframe(11.0F);
		    animator.scale(this.model, 0.5F, 0.5F, 0.5F);
		    animator.apply(Easing.EASE_OUT_BACK);
	    }
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        this.model.render(poseStack, buffer, packedLight, packedOverlay);
    }
}

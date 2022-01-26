package net.bottomtextdanny.danny_expannny.vertex_models.guns;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCSimpleModel;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCVoxel;
import net.minecraft.client.renderer.RenderType;

public class GolemHandgunModel extends BCSimpleModel {
	private final BCVoxel model;
	private final BCVoxel shotty_thing;
	private final BCVoxel grip_gear;
	private final BCVoxel back_gear;
	private final BCVoxel grip;
	
	public GolemHandgunModel() {
		super(RenderType::entityCutoutNoCull);
        this.texWidth = 64;
        this.texHeight = 64;

        this.model = new BCVoxel(this);
        this.model.setPos(0.0F, 1.0F, 0.0F);
        this.model.texOffs(18, 0).addBox(-2.0F, -7.0F, -2.0F, 4.0F, 5.0F, 7.0F, 0.0F, false);
        this.model.texOffs(0, 17).addBox(-1.0F, -6.6F, -11.0F, 2.0F, 4.0F, 9.0F, 0.1F, false);
        this.model.texOffs(14, 22).addBox(-1.5F, -3.6F, -10.5F, 3.0F, 2.0F, 8.0F, 0.1F, false);
        this.model.texOffs(0, 2).addBox(0.0F, -7.5F, -11.0F, 0.0F, 1.0F, 1.0F, 0.0F, false);
        this.model.texOffs(0, 1).addBox(-1.0F, -8.0F, 3.0F, 0.0F, 1.0F, 1.0F, 0.0F, false);
        this.model.texOffs(0, 0).addBox(1.0F, -8.0F, 3.0F, 0.0F, 1.0F, 1.0F, 0.0F, false);

        this.shotty_thing = new BCVoxel(this);
        this.shotty_thing.setPos(0.0F, -4.5F, -11.5F);
        this.model.addChild(this.shotty_thing);
        this.shotty_thing.setRotationAngle(0.0F, 0.0F, 0.7854F);
        this.shotty_thing.texOffs(0, 0).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 16.0F, 0.0F, false);

        this.grip_gear = new BCVoxel(this);
        this.grip_gear.setPos(0.0F, -1.0858F, -0.5711F);
        this.model.addChild(this.grip_gear);
        this.grip_gear.setRotationAngle(-0.7854F, 0.0F, 0.0F);
        this.grip_gear.texOffs(13, 12).addBox(0.0F, -2.5F, -2.5F, 0.0F, 5.0F, 5.0F, 0.0F, false);

        this.back_gear = new BCVoxel(this);
        this.back_gear.setPos(0.0F, -2.5F, 6.5F);
        this.model.addChild(this.back_gear);
        this.back_gear.setRotationAngle(-0.7854F, 0.0F, 0.0F);
        this.back_gear.texOffs(23, 12).addBox(0.0F, -2.5F, -3.5F, 0.0F, 5.0F, 5.0F, 0.0F, false);

        this.grip = new BCVoxel(this);
        this.grip.setPos(0.0F, 1.0F, 3.0F);
        this.model.addChild(this.grip);
        this.grip.setRotationAngle(0.3927F, 0.0F, 0.0F);
        this.grip.texOffs(0, 0).addBox(-1.0F, -4.0F, -2.0F, 2.0F, 8.0F, 4.0F, 0.0F, false);
	}
	
	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        this.model.render(poseStack, buffer, packedLight, packedOverlay);
	}
}

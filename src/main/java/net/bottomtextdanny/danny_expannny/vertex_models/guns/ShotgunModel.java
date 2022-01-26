package net.bottomtextdanny.danny_expannny.vertex_models.guns;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCSimpleModel;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCVoxel;
import net.minecraft.client.renderer.RenderType;

public class ShotgunModel extends BCSimpleModel {
	private final BCVoxel model;
	private final BCVoxel lowerCannon;
	private final BCVoxel grip;
	
	public ShotgunModel() {
		super(RenderType::entityCutoutNoCull);

        this.texWidth = 32;
        this.texHeight = 32;

        this.model = new BCVoxel(this);
        this.model.setPos(0.0F, 0.0F, 4.0F);
        this.model.texOffs(0, 0).addBox(-1.5F, -5.0F, -10.0F, 3.0F, 4.0F, 12.0F, 0.0F, false);
        this.model.texOffs(0, 16).addBox(-1.5F, -3.0F, -9.0F, 3.0F, 3.0F, 6.0F, 0.2F, false);
        this.model.texOffs(18, 0).addBox(-1.0F, -5.0F, -14.9F, 2.0F, 2.0F, 5.0F, -0.1F, false);
        this.model.texOffs(0, 9).addBox(0.0F, -5.9F, -13.9F, 0.0F, 1.0F, 1.0F, 0.0F, false);
        this.model.texOffs(0, 0).addBox(0.0F, -1.0F, -2.0F, 0.0F, 2.0F, 1.0F, 0.0F, false);

        this.lowerCannon = new BCVoxel(this);
        this.lowerCannon.setPos(0.0F, -2.5F, -10.0F);
        this.model.addChild(this.lowerCannon);
		setRotationAngle(this.lowerCannon, 0.0F, 0.0F, 0.7854F);
        this.lowerCannon.texOffs(12, 16).addBox(-0.5F, -0.5F, -4.0F, 1.0F, 1.0F, 4.0F, 0.0F, false);

        this.grip = new BCVoxel(this);
        this.grip.setPos(0.0F, -1.4F, 0.7F);
        this.model.addChild(this.grip);
		setRotationAngle(this.grip, 0.7854F, 0.0F, 0.0F);
        this.grip.texOffs(0, 0).addBox(-1.5F, -1.9F, -1.5F, 3.0F, 7.0F, 3.0F, -0.1F, false);
	}
    
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        this.model.render(poseStack, buffer, packedLight, packedOverlay);
    }
}

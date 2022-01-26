package net.bottomtextdanny.danny_expannny.vertex_models.guns;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCSimpleModel;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCVoxel;
import net.minecraft.client.renderer.RenderType;

public class MusketModel extends BCSimpleModel {
	private final BCVoxel model;
	private final BCVoxel gunny;
	
	public MusketModel() {
		super(RenderType::entityCutoutNoCull);
        this.texWidth = 32;
        this.texHeight = 32;

        this.model = new BCVoxel(this);
        this.model.setPos(0.0F, 0.0F, 4.0F);

        this.gunny = new BCVoxel(this);
        this.gunny.setPos(0.0F, 0.0F, -5.0F);
        this.model.addChild(this.gunny);
        this.gunny.texOffs(0, 0).addBox(-1.0F, -5.0F, -6.0F, 2.0F, 2.0F, 12.0F, 0.0F, false);
        this.gunny.texOffs(16, 0).addBox(-1.0F, -4.0F, -4.0F, 2.0F, 2.0F, 6.0F, 0.1F, false);
        this.gunny.texOffs(0, 14).addBox(-0.5F, -5.0F, -13.0F, 1.0F, 1.0F, 7.0F, 0.0F, false);
        this.gunny.texOffs(0, 22).addBox(-1.0F, -4.0F, 6.0F, 2.0F, 2.0F, 4.0F, 0.0F, false);
        this.gunny.texOffs(16, 14).addBox(-1.0F, -4.0F, 10.0F, 2.0F, 4.0F, 5.0F, 0.0F, false);
        this.gunny.texOffs(0, 0).addBox(0.0F, -6.0F, -12.0F, 0.0F, 1.0F, 1.0F, 0.0F, false);
        this.gunny.texOffs(0, 0).addBox(0.0F, -3.0F, 2.0F, 0.0F, 3.0F, 5.0F, 0.0F, false);
	}
	
	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        this.model.render(poseStack, buffer, packedLight, packedOverlay);
	}
}

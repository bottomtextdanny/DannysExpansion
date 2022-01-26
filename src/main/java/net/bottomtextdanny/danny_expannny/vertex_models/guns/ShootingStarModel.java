package net.bottomtextdanny.danny_expannny.vertex_models.guns;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCSimpleModel;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCVoxel;
import net.minecraft.client.renderer.RenderType;

public class ShootingStarModel extends BCSimpleModel {
	private final BCVoxel model;
	private final BCVoxel holder0;
	private final BCVoxel resize0;
	
	public ShootingStarModel() {
		super(RenderType::entityCutoutNoCull);
        this.texWidth = 64;
        this.texHeight = 64;

        this.model = new BCVoxel(this);
        this.model.setPos(0.0F, 0.0F, 8.0F);
        this.model.texOffs(23, 6).addBox(-1.5F, -6.0F, 0.0F, 3.0F, 6.0F, 9.0F, 0.0F, false);
        this.model.texOffs(0, 0).addBox(0.0F, -11.0F, -21.0F, 0.0F, 12.0F, 31.0F, 0.0F, false);

        this.holder0 = new BCVoxel(this);
        this.holder0.setPos(0.0F, 0.0F, 0.0F);
        this.model.addChild(this.holder0);
        this.holder0.texOffs(0, 15).addBox(-1.0F, -10.0F, -11.0F, 2.0F, 2.0F, 11.0F, 0.0F, false);
        this.holder0.texOffs(0, 43).addBox(-1.0F, -10.0F, -11.0F, 2.0F, 2.0F, 11.0F, 0.35F, false);
        this.holder0.texOffs(0, 0).addBox(-1.5F, -4.0F, -23.0F, 3.0F, 2.0F, 13.0F, 0.0F, false);

        this.resize0 = new BCVoxel(this);
        this.resize0.setPos(0.0F, 0.0F, 0.0F);
        this.holder0.addChild(this.resize0);
        this.resize0.texOffs(0, 0).addBox(-1.0F, -7.0F, -2.0F, 2.0F, 4.0F, 4.0F, 0.0F, false);
        this.resize0.texOffs(0, 8).addBox(-0.5F, -2.2F, -2.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);
        this.resize0.texOffs(15, 17).addBox(-1.0F, -7.0F, -15.0F, 2.0F, 3.0F, 4.0F, 0.0F, false);
        this.resize0.texOffs(35, 15).addBox(-0.5F, -5.0F, -27.0F, 1.0F, 1.0F, 12.0F, 0.2F, false);
        this.resize0.texOffs(38, 0).addBox(-1.0F, -7.0F, -11.0F, 2.0F, 6.0F, 9.0F, 0.0F, false);
        this.resize0.texOffs(19, 0).addBox(-0.5F, -1.0F, -6.0F, 1.0F, 5.0F, 3.0F, 0.0F, false);
        this.resize0.texOffs(0, 15).addBox(-0.5F, -1.0F, -6.0F, 1.0F, 5.0F, 3.0F, 0.0F, false);
	}

    @Override
    public void renderToBuffer(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        this.model.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }
}

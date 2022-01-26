package net.bottomtextdanny.danny_expannny.vertex_models.guns;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCSimpleModel;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCVoxel;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class HandgunModel extends BCSimpleModel {
    private final BCVoxel model;
    private final BCVoxel grip;
    private final BCVoxel head;

    private HandgunModel() {
        super(RenderType::entityCutoutNoCull);
        this.texWidth = 64;
        this.texHeight = 64;

        this.model = new BCVoxel(this);
        this.model.setPos(0.0F, 0.0F, 0.0F);


        this.grip = new BCVoxel(this);
        this.grip.setPos(0.0F, -0.7F, 2.5F);
        this.model.addChild(this.grip);
        this.grip.setRotationAngle(0.2182F, 0.0F, 0.0F);
        this.grip.texOffs(20, 9).addBox(-1.5F, 0.0F, -2.0F, 3.0F, 6.0F, 4.0F, -0.2F, false);

        this.head = new BCVoxel(this);
        this.head.setPos(-1.0F, 9.5F, -2.0F);
        this.model.addChild(this.head);
        this.head.texOffs(0, 0).addBox(1.0F, -9.7F, 0.3F, 0.0F, 2.0F, 3.0F, 0.0F, false);
        this.head.texOffs(0, 0).addBox(-0.5F, -13.6F, -9.7F, 3.0F, 4.0F, 9.0F, -0.1F, false);
        this.head.texOffs(0, 13).addBox(-0.5F, -13.6F, -0.8F, 3.0F, 4.0F, 8.0F, 0.0F, false);
        this.head.texOffs(0, 25).addBox(1.0F, -15.5F, -10.05F, 0.0F, 2.0F, 3.0F, 0.0F, false);
        this.head.texOffs(0, 0).addBox(-0.25F, -14.5F, 5.25F, 0.0F, 1.0F, 1.0F, 0.0F, false);
        this.head.texOffs(0, 0).addBox(2.25F, -14.5F, 5.25F, 0.0F, 1.0F, 1.0F, 0.0F, false);
        this.head.texOffs(0, 30).addBox(1.0F, -13.0F, 7.0F, 0.0F, 4.0F, 3.0F, 0.0F, false);
        this.head.texOffs(0, 6).addBox(-0.5F, -13.6F, -10.45F, 3.0F, 3.0F, 1.0F, -0.1F, false);
    }

    public static HandgunModel create() {
        return new HandgunModel();
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        this.model.render(poseStack, buffer, packedLight, packedOverlay);
    }
}

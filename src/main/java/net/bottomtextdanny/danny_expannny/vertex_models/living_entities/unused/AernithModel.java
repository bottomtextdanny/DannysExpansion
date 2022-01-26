package net.bottomtextdanny.danny_expannny.vertex_models.living_entities.unused;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCEntityModel;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCVoxel;
import net.bottomtextdanny.dannys_expansion.common.Entities.living.floating.AernithEntity;

public class AernithModel extends BCEntityModel<AernithEntity> {
    private final BCVoxel body;
    private final BCVoxel head;
    private final BCVoxel jaw;
    private final BCVoxel body2;
    private final BCVoxel body3;
    private final BCVoxel rightWing;
    private final BCVoxel rightWing2;
    private final BCVoxel leftWing;
    private final BCVoxel leftWing2;

    public AernithModel() {
        this.texWidth = 80;
        this.texHeight = 80;

        this.body = new BCVoxel(this);
        this.body.setPos(0.0F, 24.0F, 0.0F);
        this.body.texOffs(0, 0).addBox(-3.0F, -5.0F, -9.0F, 6.0F, 5.0F, 14.0F, 0.0F, false);

        this.head = new BCVoxel(this);
        this.head.setPos(0.0F, -2.5F, -9.0F);
        this.body.addChild(this.head);
        this.head.texOffs(0, 19).addBox(-6.0F, -4.5F, -6.0F, 12.0F, 6.0F, 6.0F, 0.0F, false);

        this.jaw = new BCVoxel(this);
        this.jaw.setPos(0.0F, 1.5F, 0.0F);
        this.head.addChild(this.jaw);
        this.jaw.texOffs(0, 31).addBox(-6.0F, 0.0F, -6.0F, 12.0F, 2.0F, 6.0F, 0.0F, false);

        this.body2 = new BCVoxel(this);
        this.body2.setPos(0.0F, -2.5F, 5.0F);
        this.body.addChild(this.body2);
        this.body2.texOffs(40, 0).addBox(-2.0F, -2.0F, 0.0F, 4.0F, 4.0F, 14.0F, 0.0F, false);

        this.body3 = new BCVoxel(this);
        this.body3.setPos(0.0F, 0.0F, 14.0F);
        this.body2.addChild(this.body3);
        this.body3.texOffs(40, 18).addBox(-1.5F, -1.0F, 0.0F, 3.0F, 2.0F, 14.0F, 0.0F, false);

        this.rightWing = new BCVoxel(this);
        this.rightWing.setPos(-3.0F, -3.0F, -2.0F);
        this.body.addChild(this.rightWing);
        this.rightWing.texOffs(0, 39).addBox(-6.0F, -1.0F, -6.0F, 6.0F, 2.0F, 12.0F, 0.0F, false);

        this.rightWing2 = new BCVoxel(this);
        this.rightWing2.setPos(-6.0F, 0.0F, -6.0F);
        this.rightWing.addChild(this.rightWing2);
        this.rightWing2.texOffs(0, 53).addBox(-18.0F, -0.5F, 0.0F, 18.0F, 1.0F, 18.0F, 0.0F, false);

        this.leftWing = new BCVoxel(this);
        this.leftWing.setPos(3.0F, -3.0F, -2.0F);
        this.body.addChild(this.leftWing);
        this.leftWing.texOffs(0, 39).addBox(0.0F, -1.0F, -6.0F, 6.0F, 2.0F, 12.0F, 0.0F, true);

        this.leftWing2 = new BCVoxel(this);
        this.leftWing2.setPos(6.0F, 0.0F, -6.0F);
        this.leftWing.addChild(this.leftWing2);
        this.leftWing2.texOffs(0, 53).addBox(0.0F, -0.5F, 0.0F, 18.0F, 1.0F, 18.0F, 0.0F, true);

        setupDefaultState();
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        this.body.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    public void setRotationAngle(BCVoxel modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}

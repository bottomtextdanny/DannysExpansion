package net.bottomtextdanny.danny_expannny.vertex_models.living_entities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCEntityModel;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCVoxel;
import net.bottomtextdanny.danny_expannny.objects.entities.mob.varado.Varado;

public class VaradoModel extends BCEntityModel<Varado> {
    private final BCVoxel model;
    private final BCVoxel hip;
    private final BCVoxel rightArm;
    private final BCVoxel leftArm;
    private final BCVoxel head;
    private final BCVoxel rightLeg;
    private final BCVoxel leftLeg;

    public VaradoModel() {
        texWidth = 64;
        texHeight = 64;

        model = new BCVoxel(this);
        model.setPos(0.0F, 24.0F, 0.0F);


        hip = new BCVoxel(this);
        hip.setPos(0.0F, -12.0F, 0.0F);
        model.addChild(hip);
        hip.texOffs(0, 16).addBox(-4.0F, -12.0F, -2.0F, 8.0F, 12.0F, 4.0F, 0.0F, false);

        rightArm = new BCVoxel(this);
        rightArm.setPos(-4.0F, -10.0F, 0.0F);
        hip.addChild(rightArm);
        rightArm.texOffs(0, 32).addBox(-4.0F, -2.0F, -2.0F, 4.0F, 15.0F, 4.0F, 0.0F, false);

        leftArm = new BCVoxel(this);
        leftArm.setPos(4.0F, -10.0F, 0.0F);
        hip.addChild(leftArm);
        leftArm.texOffs(24, 16).addBox(0.0F, -2.0F, -2.0F, 4.0F, 15.0F, 4.0F, 0.0F, false);

        head = new BCVoxel(this);
        head.setPos(0.0F, -12.0F, 0.0F);
        hip.addChild(head);
        head.texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F, false);

        rightLeg = new BCVoxel(this);
        rightLeg.setPos(-2.0F, -12.0F, 0.0F);
        model.addChild(rightLeg);
        rightLeg.texOffs(16, 35).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);

        leftLeg = new BCVoxel(this);
        leftLeg.setPos(2.0F, -12.0F, 0.0F);
        model.addChild(leftLeg);
        leftLeg.texOffs(32, 0).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);

        setupDefaultState();
    }

    @Override
    public void renderToBuffer(PoseStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        model.render(matrixStack, buffer, packedLight, packedOverlay);
    }
}

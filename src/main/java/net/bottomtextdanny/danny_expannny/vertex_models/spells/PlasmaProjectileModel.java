package net.bottomtextdanny.danny_expannny.vertex_models.spells;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCEntityModel;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCVoxel;
import net.bottomtextdanny.dannys_expansion.common.Entities.spell.PlasmaProjectileEntity;

public class PlasmaProjectileModel extends BCEntityModel<PlasmaProjectileEntity> {
    private final BCVoxel plasma0;
    private final BCVoxel plasma1;

    public PlasmaProjectileModel() {
        this.texWidth = 32;
        this.texHeight = 32;

        this.plasma0 = new BCVoxel(this);
        this.plasma0.setPos(0.0F, -2.0F, 0.0F);
        this.plasma0.texOffs(0, 0).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, 0.0F, false);
        this.plasma0.texOffs(0, 0).addBox(0.0F, -5.0F, -5.0F, 0.0F, 10.0F, 14.0F, 0.0F, false);

        this.plasma1 = new BCVoxel(this);
        this.plasma1.setPos(0.0F, 0.0F, 0.0F);
        this.plasma0.addChild(this.plasma1);
        setRotationAngle(this.plasma1, 0.0F, 0.0F, 1.5708F);
        this.plasma1.texOffs(0, 0).addBox(0.0F, -5.0F, -5.0F, 0.0F, 10.0F, 14.0F, 0.0F, false);

        setupDefaultState();
    }

    @Override
    public void handleRotations(PlasmaProjectileEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch) {
        this.plasma0.xRot += headPitch * RAD;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        this.plasma0.render(poseStack, buffer, packedLight, packedOverlay);
    }
}

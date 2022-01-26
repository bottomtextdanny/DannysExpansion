package net.bottomtextdanny.danny_expannny.vertex_models.spells;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCEntityModel;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCVoxel;
import net.bottomtextdanny.dannys_expansion.common.Entities.spell.IceBulletEntity;

public class IceBulletModel extends BCEntityModel<IceBulletEntity> {
    private final BCVoxel bullet;

    public IceBulletModel() {
        this.texWidth = 32;
        this.texHeight = 32;

        this.bullet = new BCVoxel(this);
        this.bullet.setPos(0.0F, -2.5F, 0.0F);
        this.bullet.texOffs(0, 0).addBox(-1.0F, -1.0F, -6.0F, 2.0F, 2.0F, 12.0F, 0.0F, false);
        this.bullet.texOffs(0, 14).addBox(-1.0F, -1.0F, -6.0F, 2.0F, 2.0F, 12.0F, 0.2F, false);

        setupDefaultState();
    }

    @Override
    public void handleRotations(IceBulletEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch) {
        this.bullet.xRot = headPitch * RAD;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        this.bullet.render(poseStack, buffer, packedLight, packedOverlay);
    }
}

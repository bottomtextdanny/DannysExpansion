package net.bottomtextdanny.danny_expannny.vertex_models.spells;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCEntityModel;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCVoxel;
import net.bottomtextdanny.dannys_expansion.common.Entities.spell.CursedFireEntity;

public class CursedFireModel extends BCEntityModel<CursedFireEntity> {
    private final BCVoxel fire;

    public CursedFireModel() {
        this.texWidth = 14;
        this.texHeight = 7;

        this.fire = new BCVoxel(this);
        this.fire.setPos(0.0F, -1.5F, 0.0F);
        this.fire.texOffs(0, 0).addBox(-1.5F, -1.5F, -2.0F, 3.0F, 3.0F, 4.0F, 0.0F, false);

        setupDefaultState();
    }

    @Override
    public void handleRotations(CursedFireEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch) {
        this.fire.xRot += headPitch * RAD;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        this.fire.render(poseStack, buffer, packedLight, packedOverlay);
    }
}

package net.bottomtextdanny.danny_expannny.vertex_models.kites;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCEntityModel;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCVoxel;
import net.bottomtextdanny.danny_expannny.objects.entities.kite.KiteKnotEntity;

public class KiteKnotModel <E extends KiteKnotEntity> extends BCEntityModel<E> {
    private final BCVoxel knot;

    public KiteKnotModel() {
        this.texWidth = 32;
        this.texHeight = 16;

        this.knot = new BCVoxel(this);
        this.knot.setPos(0.0F, 0.0F, 0.0F);
        this.knot.texOffs(0, 0).addBox(-2.5F, -2.5F, -2.5F, 5.0F, 5.0F, 5.0F, 0.0F, false);

        setupDefaultState();
    }

    @Override
    public void setupAnim(E entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        super.setupAnim(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

        this.knot.y = -2.5F;
        this.knot.yRot = netHeadYaw * ((float)Math.PI / 180F);
        this.knot.xRot = headPitch * ((float)Math.PI / 180F);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        this.knot.render(poseStack, buffer, packedLight, packedOverlay);
    }
}

package net.bottomtextdanny.danny_expannny.vertex_models.kites;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCEntityModel;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCVoxel;
import net.bottomtextdanny.danny_expannny.objects.entities.kite.KiteEntity;
import net.bottomtextdanny.dannys_expansion.core.Util.DEMath;
import net.minecraft.util.Mth;

public abstract class KiteBaseModel <E extends KiteEntity> extends BCEntityModel<E> {
    public BCVoxel kite;

    @Override
    public void setupAnim(E entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        super.setupAnim(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        float easedPosDifference = Mth.lerp(getPartialTick(), entityIn.prevRenderDifference, entityIn.renderDifference);

        float zTurbulence = DEMath.cos((ageInTicks + entityIn.tickOffset) * 0.05F) + DEMath.cos((ageInTicks + entityIn.tickOffset) * 0.02F);
        float yTurbulence = DEMath.cos((ageInTicks + entityIn.tickOffset) * 0.655F + easedPosDifference * 20) + DEMath.cos((ageInTicks + entityIn.tickOffset) * 0.267F + easedPosDifference * 20);

        setRotationAngle(this.kite, RAD * 32 + Mth.clamp(easedPosDifference * 25F, 0, 45) * RAD, yTurbulence * RAD * (3 + easedPosDifference * 140), zTurbulence * RAD * 5);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        this.kite.render(poseStack, buffer, packedLight, packedOverlay);
    }

    public abstract int kiteIndex();
}

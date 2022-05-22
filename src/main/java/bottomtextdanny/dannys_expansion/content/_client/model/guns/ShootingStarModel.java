package bottomtextdanny.dannys_expansion.content._client.model.guns;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import bottomtextdanny.braincell.mod._base.rendering.core_modeling.BCJoint;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ShootingStarModel extends GunModel {
    private final BCJoint model;
    private final BCJoint holder0;
    private final BCJoint resize0;

    public ShootingStarModel() {
        texWidth = 64;
        texHeight = 64;

        model = new BCJoint(this);
        model.setPos(0.0F, 0.0F, 0.0F);
        model.uvOffset(23, 6).addBox(-1.5F, -6.0F, 4.5F, 3.0F, 6.0F, 9.0F, 0.0F, false);
        model.uvOffset(0, 0).addBox(0.0F, -11.0F, -16.5F, 0.0F, 12.0F, 31.0F, 0.0F, false);

        holder0 = new BCJoint(this);
        holder0.setPos(0.0F, 0.0F, 0.0F);
        model.addChild(holder0);
        holder0.uvOffset(0, 15).addBox(-1.0F, -10.0F, -6.5F, 2.0F, 2.0F, 11.0F, 0.0F, false);
        holder0.uvOffset(0, 43).addBox(-1.0F, -10.0F, -6.5F, 2.0F, 2.0F, 11.0F, 0.35F, false);
        holder0.uvOffset(0, 0).addBox(-1.5F, -4.0F, -18.5F, 3.0F, 2.0F, 13.0F, 0.0F, false);

        resize0 = new BCJoint(this);
        resize0.setPos(0.0F, 0.0F, 4.5F);
        holder0.addChild(resize0);
        resize0.uvOffset(0, 0).addBox(-1.0F, -7.0F, -2.0F, 2.0F, 4.0F, 4.0F, 0.0F, false);
        resize0.uvOffset(0, 8).addBox(-0.5F, -2.2F, -2.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);
        resize0.uvOffset(15, 17).addBox(-1.0F, -7.0F, -15.0F, 2.0F, 3.0F, 4.0F, 0.0F, false);
        resize0.uvOffset(35, 15).addBox(-0.5F, -5.0F, -27.0F, 1.0F, 1.0F, 12.0F, 0.2F, false);
        resize0.uvOffset(38, 0).addBox(-1.0F, -7.0F, -11.0F, 2.0F, 6.0F, 9.0F, 0.0F, false);
        resize0.uvOffset(19, 0).addBox(-0.5F, -1.0F, -6.0F, 1.0F, 5.0F, 3.0F, 0.0F, false);
        resize0.uvOffset(0, 15).addBox(-0.5F, -1.0F, -6.0F, 1.0F, 5.0F, 3.0F, 0.0F, false);

        fire = new BCJoint(this);
        fire.setPos(0.0F, -4.5F, -29.0F);
        resize0.addChild(fire);
    }

    @Override
    public void renderToBuffer(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        this.model.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }
}

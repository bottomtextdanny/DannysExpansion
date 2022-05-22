package bottomtextdanny.dannys_expansion.content._client.model.guns;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import bottomtextdanny.braincell.mod._base.rendering.core_modeling.BCJoint;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class HandgunModel extends GunModel {
    private final BCJoint root;
    private final BCJoint barrel;
    private final BCJoint handle;

    public HandgunModel() {
        texWidth = 32;
        texHeight = 32;

        root = new BCJoint(this);
        root.setPosCore(0.0F, 0.0F, 0.0F);

        barrel = new BCJoint(this);
        barrel.setPosCore(0.5F, -4.0F, -2.625F);
        root.addChild(barrel);
        barrel.uvOffset(0, 0).addBox(-1.5F, -1.0F, -4.875F, 3.0F, 3.0F, 8.0F, 0.0F, false);
        barrel.uvOffset(0, 1).addBox(0.0F, -2.0F, 3.125F, 0.0F, 1.0F, 1.0F, 0.0F, false);
        barrel.uvOffset(0, 2).addBox(0.0F, 2.0F, 0.125F, 0.0F, 1.0F, 1.0F, 0.0F, false);
        barrel.uvOffset(0, 0).addBox(0.0F, -2.0F, -3.875F, 0.0F, 1.0F, 1.0F, 0.0F, false);

        handle = new BCJoint(this);
        handle.setPosCore(0.0F, 2.0F, 1.625F);
        barrel.addChild(handle);
        handle.uvOffset(0, 11).addBox(-1.0F, -1.0F, -0.5F, 2.0F, 4.0F, 3.0F, 0.0F, false);

        fire = new BCJoint(this);
        fire.setPosCore(0.0F, 0.5F, -9.5F);
        barrel.addChild(fire);
    }

    public static HandgunModel create() {
        return new HandgunModel();
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        this.root.render(poseStack, buffer, packedLight, packedOverlay);
    }
}

package bottomtextdanny.dannys_expansion.content._client.model.guns;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import bottomtextdanny.braincell.mod._base.rendering.core_modeling.BCJoint;

public class ShotgunModel extends GunModel {
    private final BCJoint root;

	public ShotgunModel() {
        texWidth = 64;
        texHeight = 64;

        root = new BCJoint(this);
        root.setPosCore(0.0F, 0.0F, 0.0F);
        root.uvOffset(17, 16).addBox(-2.0F, -5.0F, -12.0F, 4.0F, 3.0F, 6.0F, 0.0F, false);
        root.uvOffset(0, 16).addBox(-2.0F, -5.0F, 0.0F, 4.0F, 5.0F, 9.0F, 0.0F, false);
        root.uvOffset(19, 0).addBox(-2.5F, -7.0F, -6.0F, 5.0F, 5.0F, 7.0F, 0.0F, false);
        root.uvOffset(0, 0).addBox(0.0F, -2.0F, -2.0F, 0.0F, 1.0F, 1.0F, 0.0F, false);
        root.uvOffset(0, 0).addBox(-1.5F, -7.0F, -19.0F, 3.0F, 3.0F, 13.0F, 0.0F, false);
        root.uvOffset(0, 0).addBox(0.0F, -8.0F, -18.0F, 0.0F, 1.0F, 2.0F, 0.0F, false);

        fire = new BCJoint(this);
        fire.setPosCore(0.0F, -5.5F, -21.0F);
        root.addChild(fire);
    }
    
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        this.root.render(poseStack, buffer, packedLight, packedOverlay);
    }
}

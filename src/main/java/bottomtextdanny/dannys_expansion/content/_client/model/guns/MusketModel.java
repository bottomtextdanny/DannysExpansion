package bottomtextdanny.dannys_expansion.content._client.model.guns;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import bottomtextdanny.braincell.mod._base.rendering.core_modeling.BCJoint;

public class MusketModel extends GunModel {
    private final BCJoint root;
    private final BCJoint basething;
    private final BCJoint stock;
    private final BCJoint barrel;
    private final BCJoint backplanething;
	
	public MusketModel() {
        texWidth = 64;
        texHeight = 64;

        root = new BCJoint(this);
        root.setPosCore(0.0F, 0.0F, 0.0F);

        basething = new BCJoint(this);
        basething.setPosCore(0.0F, 0.0F, 0.0F);
        root.addChild(basething);
        basething.uvOffset(0, 0).addBox(0.0F, -2.0F, -3.0F, 0.0F, 2.0F, 7.0F, 0.0F, false);
        basething.uvOffset(0, 28).addBox(-2.0F, -6.0F, -9.0F, 4.0F, 4.0F, 13.0F, 0.0F, false);

        stock = new BCJoint(this);
        stock.setPosCore(0.0F, 0.0F, 0.0F);
        basething.addChild(stock);
        stock.uvOffset(31, 0).addBox(-2.0F, -5.0F, 4.0F, 4.0F, 5.0F, 9.0F, 0.0F, false);

        barrel = new BCJoint(this);
        barrel.setPosCore(0.0F, 0.0F, 0.0F);
        basething.addChild(barrel);
        barrel.uvOffset(0, 0).addBox(-1.5F, -7.0F, -23.0F, 3.0F, 3.0F, 25.0F, 0.0F, false);
        barrel.uvOffset(0, 0).addBox(0.0F, -8.0F, -22.0F, 0.0F, 1.0F, 1.0F, 0.0F, false);

        backplanething = new BCJoint(this);
        backplanething.setPosCore(0.0F, 0.0F, 0.0F);
        barrel.addChild(backplanething);
        backplanething.uvOffset(0, 0).addBox(0.0F, -9.0F, -1.0F, 0.0F, 2.0F, 4.0F, 0.0F, false);

        fire = new BCJoint(this);
        fire.setPosCore(0.0F, -5.5F, -25.0F);
        barrel.addChild(fire);
	}
	
	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        this.root.render(poseStack, buffer, packedLight, packedOverlay);
	}
}

package bottomtextdanny.dannys_expansion.content._client.model.entities.projectiles;

import bottomtextdanny.dannys_expansion.content.entities.mob.goblin.StoneProjectile;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import bottomtextdanny.braincell.mod._base.rendering.core_modeling.BCJoint;
import bottomtextdanny.braincell.mod.rendering.modeling.BCEntityModel;

public class StoneProjectileModel extends BCEntityModel<StoneProjectile> {
    private final BCJoint root;
    private final BCJoint body;

    public StoneProjectileModel() {
        texWidth = 16;
        texHeight = 16;

        root = new BCJoint(this, "root");
        root.setPos(0.0F, 0.0F, 0.0F);


        body = new BCJoint(this, "body");
        body.setPos(0.0F, -1.5F, 0.0F);
        root.addChild(body);
        body.uvOffset(0, 0).addBox(-1.5F, -1.5F, -1.5F, 3.0F, 3.0F, 3.0F, 0.0F, false);
    }

    @Override
    public void handleRotations(StoneProjectile entity, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch) {
        super.handleRotations(entity, limbSwing, limbSwingAmount, ageInTicks, headYaw, headPitch);

        this.body.xRot = headPitch * RAD;
        this.body.yRot = headYaw * RAD;
    }

    @Override
    public void renderToBuffer(PoseStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        root.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}

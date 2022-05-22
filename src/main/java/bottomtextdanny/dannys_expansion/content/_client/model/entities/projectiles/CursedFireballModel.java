package bottomtextdanny.dannys_expansion.content._client.model.entities.projectiles;

import bottomtextdanny.dannys_expansion.content.entities.projectile.CursedFireball;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import bottomtextdanny.braincell.mod._base.rendering.core_modeling.BCBox;
import bottomtextdanny.braincell.mod._base.rendering.core_modeling.BCJoint;
import bottomtextdanny.braincell.mod.rendering.modeling.BCEntityModel;
import net.minecraft.core.Direction;

public class CursedFireballModel extends BCEntityModel<CursedFireball> {
    private final BCJoint root;
    private final BCJoint ball;

    public CursedFireballModel() {
        texWidth = 4;
        texHeight = 4;

        root = new BCJoint(this, "root");
        root.setPos(0.0F, 0.0F, 0.0F);

        ball = new BCJoint(this, "ball");
        ball.setPos(0.0F, -2.0F, 0.0F);
        root.addChild(ball);
        ball.uvOffset(0, 0).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, 0.0F, false);

        BCBox cube = ball.getCube(0);

        cube.getQuad(Direction.UP).setDefault(this, 0, 0, 4, 4);
        cube.getQuad(Direction.DOWN).setDefault(this, 0, 0, 4, 4);
        cube.getQuad(Direction.EAST).setDefault(this, 0, 0, 4, 4);
        cube.getQuad(Direction.WEST).setDefault(this, 0, 0, 4, 4);
        cube.getQuad(Direction.SOUTH).setDefault(this, 0, 0, 4, 4);
        cube.getQuad(Direction.NORTH).setDefault(this, 0, 0, 4, 4);
    }

    @Override
    public void handleRotations(CursedFireball entity, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch) {
        ball.xRot += headPitch * RAD;
    }

    @Override
    public void renderToBuffer(PoseStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        root.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}

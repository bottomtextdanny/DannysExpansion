package bottomtextdanny.dannys_expansion.content._client.model.guns;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import bottomtextdanny.braincell.mod._base.rendering.core_modeling.BCBox;
import bottomtextdanny.braincell.mod._base.rendering.core_modeling.BCJoint;
import bottomtextdanny.braincell.mod.rendering.modeling.BCSimpleModel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Direction;

public class GunFireModel extends BCSimpleModel {
    private final BCJoint root;

    public GunFireModel() {
        super(RenderType::dragonExplosionAlpha);
        texWidth = 8;
        texHeight = 8;

        root = new BCJoint(this);
        root.setPosCore(0.0F, 0.0F, 0.0F);
        root.uvOffset(0, 0).addBox(-4.0F, -4.0F, 0.0F, 8.0F, 8.0F, 0.0F, 0.0F, false);
        root.uvOffset(0, 0).addBox(0.0F, -4.0F, -4.0F, 0.0F, 8.0F, 8.0F, 0.0F, false);
        root.uvOffset(0, 0).addBox(-4.0F, 0.0F, -4.0F, 8.0F, 0.0F, 8.0F, 0.0F, false);

        BCBox fireCube = root.getCube(0);

        fireCube.clearQuad(Direction.EAST);
        fireCube.clearQuad(Direction.WEST);
        fireCube.clearQuad(Direction.UP);
        fireCube.clearQuad(Direction.DOWN);
        fireCube.clearQuad(Direction.SOUTH);
        fireCube.getQuad(Direction.NORTH).setDefault(this, 0.0F, 0.0F, 8.0F, 8.0F);

        fireCube = root.getCube(1);

        fireCube.clearQuad(Direction.EAST);
        fireCube.clearQuad(Direction.UP);
        fireCube.clearQuad(Direction.DOWN);
        fireCube.clearQuad(Direction.SOUTH);
        fireCube.clearQuad(Direction.NORTH);
        fireCube.getQuad(Direction.WEST).setDefault(this, 0.0F, 0.0F, 8.0F, 8.0F);

        fireCube = root.getCube(2);

        fireCube.clearQuad(Direction.EAST);
        fireCube.clearQuad(Direction.WEST);
        fireCube.clearQuad(Direction.UP);
        fireCube.clearQuad(Direction.SOUTH);
        fireCube.clearQuad(Direction.NORTH);
        fireCube.getQuad(Direction.DOWN).setDefault(this, 0.0F, 0.0F, 8.0F, 8.0F);

    }

    @Override
    public void renderToBuffer(PoseStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        root.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}

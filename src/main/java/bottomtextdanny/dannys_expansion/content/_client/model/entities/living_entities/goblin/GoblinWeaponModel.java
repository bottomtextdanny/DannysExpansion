package bottomtextdanny.dannys_expansion.content._client.model.entities.living_entities.goblin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import bottomtextdanny.braincell.mod._base.rendering.core_modeling.BCJoint;
import bottomtextdanny.braincell.mod.rendering.BCRenderTypes;
import bottomtextdanny.braincell.mod.rendering.modeling.BCSimpleModel;

public class GoblinWeaponModel extends BCSimpleModel {
    private final BCJoint root;

    public GoblinWeaponModel() {
        super(BCRenderTypes::getFlatShading);
        texWidth = 64;
        texHeight = 64;

        root = new BCJoint(this, "root");
        root.setPosCore(0.0F, 0.0F, 0.0F);
        root.uvOffset(0, 0).addBox(0.0F, -15.5F, -16.0F, 0.0F, 32.0F, 32.0F, 0.0F, false);
    }

    @Override
    public void renderToBuffer(PoseStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        root.render(matrixStack, buffer, packedLight, packedOverlay);
    }
}

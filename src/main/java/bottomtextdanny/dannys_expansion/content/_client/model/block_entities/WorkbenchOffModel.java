package bottomtextdanny.dannys_expansion.content._client.model.block_entities;

import bottomtextdanny.dannys_expansion.content.block_entities.WorkbenchBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import bottomtextdanny.braincell.mod.rendering.modeling.BCBlockEntityModel;
import bottomtextdanny.braincell.mod._base.rendering.core_modeling.BCJoint;

public class WorkbenchOffModel extends BCBlockEntityModel<WorkbenchBlockEntity> {
    private final BCJoint root;

    public WorkbenchOffModel() {
        super();
        texWidth = 128;
        texHeight = 128;

        root = new BCJoint(this);
        root.setPosCore(0.0F, 0.0F, 0.0F);
        root.uvOffset(0, 19).addBox(2.0F, -5.0F, 3.0F, 4.0F, 5.0F, 4.0F, 0.0F, true);
        root.uvOffset(0, 0).addBox(2.0F, -5.0F, -7.0F, 4.0F, 5.0F, 4.0F, 0.0F, true);
        root.uvOffset(0, 38).addBox(-8.0F, -9.0F, -7.0F, 14.0F, 4.0F, 14.0F, 0.0F, false);
        root.uvOffset(0, 0).addBox(-8.0F, -12.0F, -8.0F, 16.0F, 3.0F, 16.0F, 0.0F, false);
    }

    @Override
    public void renderToBuffer(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        this.root.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }
}

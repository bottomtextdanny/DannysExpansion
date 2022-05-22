package bottomtextdanny.dannys_expansion.content._client.model.block_entities;

import bottomtextdanny.dannys_expansion.content.items.IItemModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import bottomtextdanny.braincell.mod.rendering.modeling.BCSimpleModel;
import bottomtextdanny.braincell.mod._base.rendering.core_modeling.BCJoint;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.world.item.ItemStack;

public class WorkbenchModel extends BCSimpleModel implements IItemModel {
    private final BCJoint left;
    private final BCJoint right;

    public WorkbenchModel() {
        super(RenderType::entityCutoutNoCull);
        this.texWidth = 128;
        this.texHeight = 128;

        left = new BCJoint(this);
        left.setPos(7.0F, 0.0F, -8.0F);
        left.uvOffset(0, 19).addBox(-14.0F, -5.0F, 11.0F, 4.0F, 5.0F, 4.0F, 0.0F, false);
        left.uvOffset(0, 0).addBox(-14.0F, -5.0F, 1.0F, 4.0F, 5.0F, 4.0F, 0.0F, false);
        left.uvOffset(0, 56).addBox(-14.0F, -9.0F, 1.0F, 14.0F, 4.0F, 14.0F, 0.0F, false);
        left.uvOffset(0, 19).addBox(-16.0F, -12.0F, 0.0F, 16.0F, 3.0F, 16.0F, 0.0F, false);

        right = new BCJoint(this);
        right.setPos(16.0F, 0.0F, 0.0F);
        left.addChild(right);
        right.uvOffset(0, 19).addBox(-6.0F, -5.0F, 11.0F, 4.0F, 5.0F, 4.0F, 0.0F, true);
        right.uvOffset(0, 0).addBox(-6.0F, -5.0F, 1.0F, 4.0F, 5.0F, 4.0F, 0.0F, true);
        right.uvOffset(0, 38).addBox(-16.0F, -9.0F, 1.0F, 14.0F, 4.0F, 14.0F, 0.0F, false);
        right.uvOffset(0, 0).addBox(-16.0F, -12.0F, 0.0F, 16.0F, 3.0F, 16.0F, 0.0F, false);
    }

    @Override
    public void renderToBuffer(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        this.left.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }

    @Override
    public void pre(ItemStack stack, ItemTransforms.TransformType transformType, PoseStack poseStack, MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
        poseStack.mulPose(Vector3f.XP.rotation(180.0F * ((float)Math.PI / 180)));
    }

    @Override
    public void post(ItemStack stack, ItemTransforms.TransformType transformType, PoseStack poseStack, MultiBufferSource buffer, int combinedLight, int combinedOverlay) {

    }
}

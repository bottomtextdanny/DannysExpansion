package net.bottomtextdanny.danny_expannny.vertex_models.block_entities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCBlockEntityModel;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCVoxel;
import net.bottomtextdanny.danny_expannny.objects.block_entities.WorkbenchBlockEntity;

public class WorkbenchMainModel extends BCBlockEntityModel<WorkbenchBlockEntity> {
    private final BCVoxel model;
    private final BCVoxel book;

    public WorkbenchMainModel() {
        super();
        this.texWidth = 128;
        this.texHeight = 128;

        this.model = new BCVoxel(this);
        this.model.setPos(0.0F, 0.0F, 0.0F);
        this.model.texOffs(0, 0).addBox(-8.0F, -14.0F, -8.0F, 16.0F, 2.0F, 16.0F, 0.0F, false);
        this.model.texOffs(0, 46).addBox(-6.0F, -12.0F, -7.0F, 14.0F, 6.0F, 14.0F, 0.0F, false);
        this.model.texOffs(0, 18).addBox(-6.0F, -12.0F, -7.0F, 3.0F, 12.0F, 3.0F, 0.0F, false);
        this.model.texOffs(12, 18).addBox(-6.0F, -12.0F, 4.0F, 3.0F, 12.0F, 3.0F, 0.0F, false);

        this.book = new BCVoxel(this);
        this.book.setPos(0.0F, -16.0F, 0.0F);
        this.model.addChild(this.book);
        this.book.yRot = 0.1745F;
        this.book.texOffs(0, 33).addBox(-5.0F, -0.05F, -4.0F, 8.0F, 2.0F, 10.0F, 0.0F, false);
        this.book.texOffs(8, 33).addBox(-5.0F, 1.95F, -4.0F, 8.0F, 0.0F, 10.0F, 0.0F, false);
        this.book.texOffs(0, 66).addBox(-4.75F, -0.05F, -3.5F, 7.0F, 2.0F, 9.0F, 0.25F, 0.0F, 0.0F);
    }

    @Override
    public void renderToBuffer(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {

        this.model.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }
}

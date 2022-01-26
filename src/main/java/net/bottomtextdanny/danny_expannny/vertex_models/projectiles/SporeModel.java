package net.bottomtextdanny.danny_expannny.vertex_models.projectiles;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCSimpleModel;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCVoxel;
import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class SporeModel extends BCSimpleModel {
    public static final ResourceLocation TEXTURES = new ResourceLocation(DannysExpansion.ID, "textures/entity/projectile/spore.png");
    private final BCVoxel model;

    public SporeModel() {
        super(RenderType::entityTranslucent);
        this.texWidth = 16;
        this.texHeight = 8;

        this.model = new BCVoxel(this);
        this.model.setPos(0.0F, -2.0F, 0.0F);
        this.model.texOffs(0, 0).addBox(-2.0F, -2.5F, -2.0F, 4.0F, 4.0F, 4.0F, 0.0F, false);
        this.model.texOffs(0, 0).addBox(-2.0F, -2.5F, -2.0F, 4.0F, 4.0F, 4.0F, 0.5F, true);
    }

    public void renderToBuffer(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        this.model.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }
}

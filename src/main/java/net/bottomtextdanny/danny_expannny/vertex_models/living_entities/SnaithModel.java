package net.bottomtextdanny.danny_expannny.vertex_models.living_entities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCEntityModel;
import net.bottomtextdanny.braincell.mod.minecraft_front_rendering.vertex.BCVoxel;
import net.bottomtextdanny.dannys_expansion.common.Entities.living.SnaithEntity;

public class SnaithModel extends BCEntityModel<SnaithEntity> {
    private final BCVoxel model;
    private final BCVoxel body;
    private final BCVoxel sludge;
    private final BCVoxel tail;
    private final BCVoxel rightAntenna;
    private final BCVoxel leftAntenna;
    private final BCVoxel head;
    private final BCVoxel shell;

    public SnaithModel() {
        this.texWidth = 256;
        this.texHeight = 256;

        this.model = new BCVoxel(this);
        this.model.setPos(0.0F, 24.0F, 0.0F);


        this.body = new BCVoxel(this);
        this.body.setPos(0.0F, 0.0F, 0.0F);
        this.model.addChild(this.body);


        this.sludge = new BCVoxel(this);
        this.sludge.setPos(0.0F, -7.0F, 0.0F);
        this.body.addChild(this.sludge);


        this.tail = new BCVoxel(this);
        this.tail.setPos(0.0F, 3.5F, 0.0F);
        this.sludge.addChild(this.tail);
        this.tail.texOffs(0, 74).addBox(-4.0F, -3.5F, 0.0F, 8.0F, 7.0F, 18.0F, 0.0F, false);

        this.rightAntenna = new BCVoxel(this);
        this.rightAntenna.setPos(-3.0F, 0.0F, -18.0F);
        this.sludge.addChild(this.rightAntenna);
        setRotationAngle(this.rightAntenna, 0.1745F, 0.0F, -0.3054F);
        this.rightAntenna.texOffs(0, 0).addBox(-2.0F, -8.0F, -2.0F, 4.0F, 8.0F, 4.0F, 0.0F, false);

        this.leftAntenna = new BCVoxel(this);
        this.leftAntenna.setPos(3.0F, 0.0F, -18.0F);
        this.sludge.addChild(this.leftAntenna);
        setRotationAngle(this.leftAntenna, 0.1745F, 0.0F, 0.3054F);
        this.leftAntenna.texOffs(0, 0).addBox(-2.0F, -8.0F, -2.0F, 4.0F, 8.0F, 4.0F, 0.0F, true);

        this.head = new BCVoxel(this);
        this.head.setPos(0.0F, 4.0F, -12.5F);
        this.sludge.addChild(this.head);
        this.head.texOffs(0, 44).addBox(-6.0F, -5.0F, -9.5F, 12.0F, 8.0F, 22.0F, 0.0F, false);

        this.shell = new BCVoxel(this);
        this.shell.setPos(0.0F, -6.0F, 2.0F);
        this.body.addChild(this.shell);
        setRotationAngle(this.shell, -0.1745F, 0.0F, 0.0F);
        this.shell.texOffs(0, 0).addBox(-10.0F, -22.0F, -11.0F, 20.0F, 22.0F, 22.0F, 0.0F, false);

        setupDefaultState();
    }

    @Override
    public void renderToBuffer(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        this.model.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }
}

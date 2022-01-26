package net.bottomtextdanny.braincell.mod.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Matrix4f;
import net.minecraft.client.renderer.GameRenderer;

//goes down !!
public record BlittyStepDown(ImageData image, int x, int y, int width, int height) {

    public void render(PoseStack matrixStack, float posX, float posY, float posZ, int step) {
        Matrix4f matrix = matrixStack.last().pose();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        int relativeY = this.y + this.height * step;
        float x0 = this.x / (float)this.image.width;
        float y0 = relativeY / (float)this.image.height;
        float x1 = (this.x + this.width) / (float)this.image.width;
        float y1 = (relativeY + this.height) / (float)this.image.height;
        BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        bufferbuilder.vertex(matrix, posX, posY + this.height, posZ).uv(x0, y1).endVertex();
        bufferbuilder.vertex(matrix, posX + this.width, posY + this.height, posZ).uv(x1, y1).endVertex();
        bufferbuilder.vertex(matrix, posX + this.width, posY, posZ).uv(x1, y0).endVertex();
        bufferbuilder.vertex(matrix, posX, posY, posZ).uv(x0, y0).endVertex();
        bufferbuilder.end();
        BufferUploader.end(bufferbuilder);
    }
}

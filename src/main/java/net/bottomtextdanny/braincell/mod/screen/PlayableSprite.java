package net.bottomtextdanny.braincell.mod.screen;

import com.mojang.blaze3d.vertex.PoseStack;

public final class PlayableSprite {
    private final BlittyStepDown blitty;
    private final int sprites;
    private final int framesPerSprite;
    private int subSpriteCounter;

    public PlayableSprite(BlittyStepDown blitty, int sprites, int framesPerSprite) {
        super();
        this.blitty = blitty;
        this.sprites = sprites;
        this.framesPerSprite = framesPerSprite;
        this.subSpriteCounter = framesPerSprite * sprites + 1;
    }

    public void render(PoseStack matrixStack, float posX, float posY, float posZ) {
        if (this.subSpriteCounter < this.framesPerSprite * this.sprites) {
            this.blitty.render(matrixStack, posX, posY, posZ, this.subSpriteCounter / this.framesPerSprite);
            this.subSpriteCounter++;
        } else {
            this.blitty.render(matrixStack, posX, posY, posZ, 0);
        }
    }

    public int progression() {
        return this.subSpriteCounter / this.framesPerSprite;
    }

    public void reset() {
        this.subSpriteCounter = 0;
    }
}

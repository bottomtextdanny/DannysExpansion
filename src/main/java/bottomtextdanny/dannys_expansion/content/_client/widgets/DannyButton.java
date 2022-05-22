package bottomtextdanny.dannys_expansion.content._client.widgets;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Predicate;


public class DannyButton extends AbstractButton {
    public ClickResponse clickConsumer;
    Predicate<DannyButton> hidePredicate;
    public boolean renderCustomImage;
    public ResourceLocation[] customTextures;
    public boolean supressClickSound;
    public boolean renderName;
    public int originalX;
    public int originalY;

    public DannyButton(int x, int y, int width, int height,
                       Component title, ClickResponse clickConsumer,
                       Init init) {
        super(x, y, width, height, title);
        this.originalX = x;
        this.originalY = y;
        this.clickConsumer = clickConsumer;

        init.init(this);
    }

    public DannyButton hideIf(Predicate<DannyButton> pred) {
        hidePredicate = pred;

        return this;
    }

    public boolean canRender() {
        return hidePredicate == null || !hidePredicate.test(this);
    }

    @Override
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        super.render(matrixStack, mouseX, mouseY, partialTicks);

        if (canRender()) {
            int state = 0;

            if (isMouseOver(mouseX, mouseY)) {
                state = 1;
            }

            if (renderCustomImage) {
                ResourceLocation texture = customTextures[state];

                RenderSystem.setShaderTexture(0, texture);

                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                RenderSystem.enableBlend();
                RenderSystem.defaultBlendFunc();
                RenderSystem.enableDepthTest();
                blit(matrixStack, this.x, this.y, 0, 0, 0, width, height, height, width);

                if (renderName) {
                    drawCenteredString(matrixStack, Minecraft.getInstance().font, this.getMessage(), x + width / 2, y + height / 2, 16777215);
                }


            } else {
                renderButton(matrixStack, mouseX, mouseY, partialTicks);
            }

            renderUnhidden(matrixStack, mouseX, mouseY, partialTicks);
        }



    }

    public void renderUnhidden(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
    }

    @Override
    public void renderButton(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        if (canRender() && !renderCustomImage) {
            super.renderButton(matrixStack, mouseX, mouseY, partialTicks);
        }
    }

    @Override
    public void onPress() {
        if (canRender()) {
            this.clickConsumer.OnClick(this);
        }
    }

    @Override
    public void onClick(double mouseX, double mouseY) {
        if (canRender()) {
            super.onClick(mouseX, mouseY);
        }
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (canRender()) {
            if (this.active && this.visible) {
                if (keyCode != 257 && keyCode != 32 && keyCode != 335) {
                    return false;
                } else {
                    if (supressClickSound) this.playDownSound(Minecraft.getInstance().getSoundManager());
                    this.onPress();
                    return true;
                }
            } else {
                return false;
            }
        }
        return false;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (canRender()) {
            return super.mouseClicked(mouseX, mouseY, button);
        }
        return false;
    }

    @Override
    public void updateNarration(NarrationElementOutput p_169152_) {
    }

    @FunctionalInterface
    public interface ClickResponse {
        void OnClick(DannyButton button);
    }

    @FunctionalInterface
    public interface Init {
        void init(DannyButton button);
    }
}

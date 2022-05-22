package bottomtextdanny.dannys_expansion.content._client.screens;

import bottomtextdanny.dannys_expansion.content.containers.DannyAccessoriesContainer;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

public class AccessoriesScreen extends AbstractContainerScreen<DannyAccessoriesContainer> {
    private static final ResourceLocation ACCESSORIES_TEXTURE = new ResourceLocation("dannys_expansion:textures/gui/container/accessories.png");
    public TranslatableComponent name;

    public AccessoriesScreen(DannyAccessoriesContainer screenContainer, Inventory inv, Component titleIn) {
        super(screenContainer, inv, titleIn);
        this.name = new TranslatableComponent("container.danny_accessories");

    }

    @Override
    protected void renderBg(PoseStack matrixStack, float partialTicks, int x, int y) {
        this.renderBackground(matrixStack);
        RenderSystem.setShaderTexture(0, ACCESSORIES_TEXTURE);
        blit(matrixStack, width / 2 - 88, height / 2 - 65, 0, 0, 0, 176, 130, 256, 256);

    }

    protected void renderLabels(PoseStack matrixStack, int x, int y) {

    }
    @Override
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        TooltipFlag.Default tooltipFlag = Minecraft.getInstance().options.advancedItemTooltips ? TooltipFlag.Default.ADVANCED : TooltipFlag.Default.NORMAL;

        MultiBufferSource.BufferSource renderTypeBuffer = MultiBufferSource.immediate(Tesselator.getInstance().getBuilder());

        this.font.drawInBatch(this.name.getString(), width / 2 - 80, height / 2 - 28, 4210752, false, matrixStack.last().pose(), renderTypeBuffer, false, 0, 15728880);

        this.font.drawInBatch(name.getString(), width / 2 - 80, height / 2 - 60, 4210752, false, matrixStack.last().pose(), renderTypeBuffer, false, 0, 15728880);

        renderTypeBuffer.endBatch();

        if (getSlotUnderMouse() != null && getSlotUnderMouse().getItem() != ItemStack.EMPTY) {
            renderComponentTooltip(matrixStack, getSlotUnderMouse().getItem().getTooltipLines(minecraft.player, tooltipFlag), mouseX, mouseY);
        }

        for (Widget button : this.renderables) {
            if (button instanceof AbstractWidget widget) {
                if (widget.isMouseOver(mouseX, mouseY)) {
                    widget.renderToolTip(matrixStack, mouseX, mouseY);
                }
            }
        }
    }


    @Override
    public void onClose() {
        super.onClose();
        Minecraft.getInstance().setScreen(new InventoryScreen(Minecraft.getInstance().player));
    }
}

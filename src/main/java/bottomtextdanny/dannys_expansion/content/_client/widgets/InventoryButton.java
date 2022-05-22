package bottomtextdanny.dannys_expansion.content._client.widgets;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.network.chat.Component;

public class InventoryButton extends DannyButton {
    EffectRenderingInventoryScreen<?> inventory;
    int guiLeftHolder;

    public InventoryButton(EffectRenderingInventoryScreen<?> inv, int x, int y, int width, int height, Component title, ClickResponse clickConsumer, Init init) {
        super(x, y, width, height, title, clickConsumer, init);
        inventory = inv;
        guiLeftHolder = inv.getGuiLeft();
    }

    @Override
    public void renderButton(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        super.renderButton(matrixStack, mouseX, mouseY, partialTicks);

        if (guiLeftHolder != inventory.getGuiLeft()) {
            guiLeftHolder = inventory.getGuiLeft();
        }

        x = originalX + guiLeftHolder;
    }
}

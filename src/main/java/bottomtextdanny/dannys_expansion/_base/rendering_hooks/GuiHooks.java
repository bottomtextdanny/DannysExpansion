package bottomtextdanny.dannys_expansion._base.rendering_hooks;

import bottomtextdanny.dannys_expansion.DannysExpansion;
import bottomtextdanny.dannys_expansion._util._client.CMC;
import bottomtextdanny.dannys_expansion._base.network.clienttoserver.MSGOpenAccessoriesMenu;
import bottomtextdanny.dannys_expansion.content._client.widgets.DannyButton;
import bottomtextdanny.dannys_expansion.content._client.widgets.InventoryButton;
import com.mojang.blaze3d.platform.Window;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.client.event.ScreenEvent;

import java.util.function.Predicate;

public final class GuiHooks {
    private static final TranslatableComponent ACCESSORIES_BUTTON_TITLE = new TranslatableComponent("container.danny_accessories");
    private static final ResourceLocation[] ACCESSORIES_BUTTON_TEXTURES = {
            new ResourceLocation(DannysExpansion.ID, "textures/gui/screen/accessories_button.png"),
            new ResourceLocation(DannysExpansion.ID, "textures/gui/screen/accessories_button_mouse_over.png")
    };

    public static void hookButtonsIfShould(ScreenEvent.InitScreenEvent.Post event) {
        LocalPlayer player = CMC.player();

        if (player != null && player.isAlive()) {
            Predicate<DannyButton> hideIf = button -> false;
            Window window = Minecraft.getInstance().getWindow();
            int centerX = 90;
            int centerY = window.getGuiScaledHeight() / 2;
            int x = 50;
            int y;

            if (event.getScreen() instanceof InventoryScreen)
                y = -22;
            else if (event.getScreen() instanceof CreativeModeInventoryScreen screen) {
                y = -40;
                hideIf = button -> screen.getSelectedTab() != CreativeModeTab.TAB_INVENTORY.getId();
            } else return;

            AbstractButton button = accessoriesButton(event.getScreen(), hideIf, centerX + x, centerY + y);

            event.addListener(button);
        }
    }

    private static AbstractButton accessoriesButton(Screen screen, Predicate<DannyButton> hide, int x, int y) {
        return new InventoryButton((EffectRenderingInventoryScreen<?>) screen, x, y, 18, 18, ACCESSORIES_BUTTON_TITLE, button -> {
            new MSGOpenAccessoriesMenu().sendToServer();
        }, button -> {
            button.renderCustomImage = true;
            button.customTextures = ACCESSORIES_BUTTON_TEXTURES;
        }).hideIf(hide);
    }

    private GuiHooks() {}
}

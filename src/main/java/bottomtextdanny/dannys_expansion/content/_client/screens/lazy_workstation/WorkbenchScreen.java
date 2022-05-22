package bottomtextdanny.dannys_expansion.content._client.screens.lazy_workstation;

import bottomtextdanny.dannys_expansion.DannysExpansion;
import bottomtextdanny.dannys_expansion.content.containers.lazy_workstation.WorkbenchMenu;
import bottomtextdanny.braincell.base.screen.ImageData;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Inventory;

public class WorkbenchScreen extends DefaultLazyCraftScreen<WorkbenchMenu> {

    public WorkbenchScreen(WorkbenchMenu screenContainer, Inventory inv, Component titleIn) {
        super(screenContainer,
                new TranslatableComponent("container.workbench"),
                new ImageData(DannysExpansion.ID, "textures/gui/container/workbench.png", 512, 512),
                inv,
                titleIn);
    }
}

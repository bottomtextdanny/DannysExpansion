package bottomtextdanny.dannys_expansion.content.containers.lazy_workstation;

import bottomtextdanny.dannys_expansion._base.lazy_recipe.LazyRecipeType;
import bottomtextdanny.dannys_expansion.content.containers.lazy_workstation.base.DefaultLazyCraftMenu;
import bottomtextdanny.dannys_expansion.tables.DEMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;

public class WorkbenchMenu extends DefaultLazyCraftMenu {

    public WorkbenchMenu(int id, Inventory playerInventory, FriendlyByteBuf data) {
        this(id, playerInventory);
    }

    public WorkbenchMenu(int id, Inventory playerInventoryIn) {
        super(id, DEMenuTypes.WORKBENCH.get(), LazyRecipeType.WORKBENCH, playerInventoryIn);
    }
}

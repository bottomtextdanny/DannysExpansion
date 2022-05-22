package bottomtextdanny.dannys_expansion.content.containers.lazy_workstation;

import bottomtextdanny.dannys_expansion._base.lazy_recipe.LazyRecipeType;
import bottomtextdanny.dannys_expansion.tables.DEMenuTypes;
import bottomtextdanny.dannys_expansion.content.containers.lazy_workstation.base.DefaultLazyCraftMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;

public class EngineeringStationMenu extends DefaultLazyCraftMenu {

    public EngineeringStationMenu(int id, Inventory playerInventory, FriendlyByteBuf data) {
        this(id, playerInventory);
    }

    public EngineeringStationMenu(int id, Inventory playerInventoryIn) {
        super(id, DEMenuTypes.ENGINEERING_STATION.get(),
                LazyRecipeType.ENGINEERING_STATION, playerInventoryIn);
    }
}

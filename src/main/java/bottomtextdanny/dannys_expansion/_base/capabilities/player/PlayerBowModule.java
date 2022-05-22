package bottomtextdanny.dannys_expansion._base.capabilities.player;

import bottomtextdanny.dannys_expansion.content.items.bow.DEBowItem;
import bottomtextdanny.braincell.mod.capability.CapabilityModule;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

public class PlayerBowModule extends CapabilityModule<Player, PlayerCapability> {

    public PlayerBowModule(PlayerCapability capability) {
        super("bow_handler", capability);
    }

    public void tick() {
        if (getHolder().getUseItem().getItem() instanceof DEBowItem item) {
            if (item.automatic() && getHolder().getTicksUsingItem() >= item.getTrueMaxCount(getHolder().getUseItem())) {
                getHolder().releaseUsingItem();
            }
        }
    }

    @Override
    public void serializeNBT(CompoundTag nbt) {}

    @Override
    public void deserializeNBT(CompoundTag nbt) {}
}

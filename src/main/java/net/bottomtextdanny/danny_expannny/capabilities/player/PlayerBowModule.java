package net.bottomtextdanny.danny_expannny.capabilities.player;

import net.bottomtextdanny.braincell.mod.capability_helper.CapabilityModule;
import net.bottomtextdanny.dannys_expansion.core.interfaces.IDannyHoldable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

public class PlayerBowModule extends CapabilityModule<Player, PlayerCapability> {

    public PlayerBowModule(PlayerCapability capability) {
        super("bow_handler", capability);
    }

    public void tick() {
        if (getHolder().getUseItem().getItem() instanceof IDannyHoldable item) {
            if (item.automatic() && getHolder().getTicksUsingItem() >= item.getTrueMaxCount(getHolder().getUseItem())) {
                getHolder().releaseUsingItem();
            }
        }
    }

    @Override
    public void serializeNBT(CompoundTag nbt) {

    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {

    }
}

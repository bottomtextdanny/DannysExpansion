package net.bottomtextdanny.danny_expannny.capabilities.player;

import net.bottomtextdanny.braincell.mod.capability_helper.CapabilityModule;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

public class PlayerTrustModule extends CapabilityModule<Player, PlayerCapability> {


    public PlayerTrustModule(String name, PlayerCapability capability) {
        super(name, capability);
    }

    @Override
    public void serializeNBT(CompoundTag nbt) {

    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {

    }

    public enum Trust {
        ENDER_BEAST
    }
}

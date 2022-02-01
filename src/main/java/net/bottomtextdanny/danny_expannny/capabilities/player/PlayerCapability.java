package net.bottomtextdanny.danny_expannny.capabilities.player;

import com.google.common.collect.ImmutableList;
import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.braincell.mod.capability_helper.CapabilityModule;
import net.bottomtextdanny.braincell.mod.capability_helper.CapabilityWrap;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DannysExpansion.ID)
public class PlayerCapability extends CapabilityWrap<PlayerCapability, Player> {
    public static Capability<PlayerCapability> TOKEN = CapabilityManager.get(new CapabilityToken<>(){});
    private PlayerAccessoryModule accessoryModule;
    private PlayerGunModule gunModule;
    private PlayerBowModule bowModule;

    public PlayerCapability(Player holder) {
        super(holder);
    }

    @Override
    protected void populateModuleList(ImmutableList.Builder<CapabilityModule<Player, PlayerCapability>> moduleList) {
        this.accessoryModule = new PlayerAccessoryModule(this);
        this.gunModule = new PlayerGunModule(this);
        this.bowModule = new PlayerBowModule(this);
        moduleList.add(this.accessoryModule);
        moduleList.add(this.gunModule);
        moduleList.add(this.bowModule);
    }

    public PlayerAccessoryModule accessoryModule() {
        return this.accessoryModule;
    }

    public PlayerGunModule gunModule() {
        return this.gunModule;
    }

    public PlayerBowModule bowModule() {
        return this.bowModule;
    }

    @Override
    protected Capability<?> getToken() {
        return TOKEN;
    }
}

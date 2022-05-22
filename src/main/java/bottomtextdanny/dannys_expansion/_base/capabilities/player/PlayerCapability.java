package bottomtextdanny.dannys_expansion._base.capabilities.player;

import bottomtextdanny.dannys_expansion.DannysExpansion;
import com.google.common.collect.ImmutableList;
import bottomtextdanny.braincell.mod.capability.CapabilityModule;
import bottomtextdanny.braincell.mod.capability.CapabilityWrap;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DannysExpansion.ID)
public class PlayerCapability extends CapabilityWrap<PlayerCapability, Player> {
    public static Capability<PlayerCapability> TOKEN = CapabilityManager.get(new CapabilityToken<>(){});
    private DEAccessoryModule accessoryModule;
    private PlayerGunModule gunModule;
    private PlayerBowModule bowModule;

    public PlayerCapability(Player holder) {
        super(holder);
    }

    @Override
    protected void populateModuleList(ImmutableList.Builder<CapabilityModule<Player, PlayerCapability>> moduleList) {
        this.accessoryModule = new DEAccessoryModule(this);
        this.gunModule = new PlayerGunModule(this);
        this.bowModule = new PlayerBowModule(this);
        moduleList.add(this.accessoryModule);
        moduleList.add(this.gunModule);
        moduleList.add(this.bowModule);
    }

    public DEAccessoryModule accessoryModule() {
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

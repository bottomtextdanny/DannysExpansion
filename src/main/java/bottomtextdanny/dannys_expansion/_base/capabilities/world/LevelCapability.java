package bottomtextdanny.dannys_expansion._base.capabilities.world;

import bottomtextdanny.dannys_expansion.DannysExpansion;
import com.google.common.collect.ImmutableList;
import bottomtextdanny.braincell.mod.capability.CapabilityModule;
import bottomtextdanny.braincell.mod.capability.CapabilityWrap;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DannysExpansion.ID)
public class LevelCapability extends CapabilityWrap<LevelCapability, Level> {
    public static Capability<LevelCapability> TOKEN = CapabilityManager.get(new CapabilityToken<>(){});
    private LevelWindModule windModule;
    private LevelPhaseModule phaseModule;

    public LevelCapability(Level holder) {
        super(holder);
    }

    @Override
    protected void populateModuleList(ImmutableList.Builder<CapabilityModule<Level, LevelCapability>> moduleList) {
        this.windModule = new LevelWindModule(this);
        this.phaseModule = new LevelPhaseModule(this);
        moduleList.add(this.windModule);
        moduleList.add(this.phaseModule);
    }

    public LevelPhaseModule getPhaseModule() {
        return this.phaseModule;
    }

    public LevelWindModule getWindModule() {
        return this.windModule;
    }

    @Override
    protected Capability<?> getToken() {
        return TOKEN;
    }
}

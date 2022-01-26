package net.bottomtextdanny.danny_expannny.capabilities.world;

import com.google.common.collect.ImmutableList;
import net.bottomtextdanny.braincell.mod.base.util.Connection;
import net.bottomtextdanny.danny_expannny.DannysExpansion;
import net.bottomtextdanny.braincell.mod.capability_helper.CapabilityModule;
import net.bottomtextdanny.braincell.mod.capability_helper.CapabilityWrap;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DannysExpansion.ID)
public class LevelCapability extends CapabilityWrap<LevelCapability, Level> {
    public static Capability<LevelCapability> CAPABILITY = CapabilityManager.get(new CapabilityToken<>(){});
    private LevelWindModule windModule;
    private LevelScheduleModule scheduleModule;
    private LevelPhaseModule phaseModule;
    private LevelCrumbsModule crumbsModule;
    private Object physicalLightModule;
    public LevelCapability(Level holder) {
        super(holder);
           }

    @Override
    protected void populateModuleList(ImmutableList.Builder<CapabilityModule<Level, LevelCapability>> moduleList) {
        this.windModule = new LevelWindModule(this);
        this.scheduleModule = new LevelScheduleModule(this);
        this.phaseModule = new LevelPhaseModule(this);
        this.crumbsModule = getHolder().isClientSide() ? null : new LevelCrumbsModule(this);
        this.physicalLightModule = Connection.makeClientSideUnknown(() -> new LevelPhysicalLightModule(this));
        moduleList.add(this.windModule);
        moduleList.add(this.scheduleModule);
        moduleList.add(this.phaseModule);
        if (!getHolder().isClientSide()) {
            moduleList.add(this.crumbsModule);
        }
        Connection.doClientSide(() -> {
            this.physicalLightModule = new LevelPhysicalLightModule(this);
            moduleList.add((LevelPhysicalLightModule)this.physicalLightModule);
        });
    }

    public LevelCrumbsModule getCrumbsModule() {
        return this.crumbsModule;
    }

    public LevelPhaseModule getPhaseModule() {
        return this.phaseModule;
    }

    public LevelScheduleModule getScheduleModule() {
        return this.scheduleModule;
    }

    public LevelWindModule getWindModule() {
        return this.windModule;
    }

    @OnlyIn(Dist.CLIENT)
    public LevelPhysicalLightModule getPhysicalLightModule() {
        return (LevelPhysicalLightModule)this.physicalLightModule;
    }

    @Override
    protected Capability<?> getToken() {
        return CAPABILITY;
    }
}

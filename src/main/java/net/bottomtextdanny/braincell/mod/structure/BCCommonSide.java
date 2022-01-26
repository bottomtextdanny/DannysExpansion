package net.bottomtextdanny.braincell.mod.structure;

import com.google.common.collect.ImmutableSet;
import net.bottomtextdanny.braincell.mod.packet_front.BCPacketInitialization;
import net.bottomtextdanny.braincell.mod.structure.common_sided.*;
import net.bottomtextdanny.braincell.mod.world.entity_utilities.EntityHurtCallout;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.fml.loading.moddiscovery.ModInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class BCCommonSide extends AbstractModSide {
    public final Logger logger;
    private final DefaultSolvingHooks solvingHooks;
    private final EntityCoreDataDeferror entityCoreDataDeferror;
    private final ChestOverriderManager chestOverriderManager;
    private final ItemSortData itemSortData;
    private SerializerLookup serializerLookUp;
    public final ImmutableSet<String> loadedMods;
    private boolean passedInitialization;

    public BCCommonSide(String modId) {
        super(modId);
        this.loadedMods = ImmutableSet.copyOf(FMLLoader.getLoadingModList().getMods()
                .stream()
                .map(ModInfo::getModId)
                .toArray(String[]::new));
        this.logger = LogManager.getLogger(String.join(modId, "(common content)"));
        BCPacketInitialization.initializeNetworkPackets();
        this.serializerLookUp = SerializerLookup.createUnbuilt();
        this.solvingHooks = new DefaultSolvingHooks();
        this.itemSortData = new ItemSortData();
        this.chestOverriderManager = new ChestOverriderManager();
        this.entityCoreDataDeferror = new EntityCoreDataDeferror();
    }

    public boolean isModThere(String modId) {
        return this.loadedMods.contains(modId);
    }

    public static BCCommonSide with(String modId) {
        return new BCCommonSide(modId);
    }

    @Override
    public void modLoadingCallOut() {
        this.entityCoreDataDeferror.sendListeners();
    }

    @Override
    public void postModLoadingPhaseCallOut() {
        this.passedInitialization = true;
        this.serializerLookUp = ((SerializerLookup.NotBuilt)this.serializerLookUp).build();
        if (BraincellModules.ENTITY_HURT_CALL_OUT.isActive()) {
            MinecraftForge.EVENT_BUS.addListener((LivingHurtEvent event) -> {
                if (event.getEntityLiving() instanceof EntityHurtCallout) {
                    event.setAmount(((EntityHurtCallout) event.getEntityLiving()).hurtCallout(event.getAmount(), event.getSource()));
                }
            });
        }
    }

    public boolean hasPassedInitialization() {
        return this.passedInitialization;
    }

    public DefaultSolvingHooks getSolvingHooks() {
        return this.solvingHooks;
    }

    public ChestOverriderManager getChestOverriderManager() {
        return this.chestOverriderManager;
    }

    public EntityCoreDataDeferror getEntityCoreDataDeferror() {
        return this.entityCoreDataDeferror;
    }

    public ItemSortData getItemSortData() {
        return this.itemSortData;
    }

    public SerializerLookup getSerializerLookUp() {
        return this.serializerLookUp;
    }
}

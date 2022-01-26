package net.bottomtextdanny.braincell;

import net.bottomtextdanny.braincell.mod.base.annotations.OnlyHandledOnClient;
import net.bottomtextdanny.braincell.mod.base.annotations.OnlyHandledOnServer;
import net.bottomtextdanny.braincell.mod.base.util.Connection;
import net.bottomtextdanny.braincell.mod.not_enforced_registry_tools.essential_features.DeferrorType;
import net.bottomtextdanny.braincell.mod.not_enforced_registry_tools.essential_features.ModDeferringManager;
import net.bottomtextdanny.braincell.mod.structure.client_sided.BCClientSide;
import net.bottomtextdanny.braincell.mod.structure.BCCommonSide;
import net.bottomtextdanny.braincell.mod.structure.DCServerSide;
import net.bottomtextdanny.braincell.mod.structure.client_sided.BCInputHelper;
import net.bottomtextdanny.braincell.mod.structure.object_tables.BraincellEntities;
import net.bottomtextdanny.braincell.mod.structure.object_tables.BraincellRecipes;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Braincell {
    public static final String NAME = "Braincell";
    public static final String ID = "braincell";
    public static final Logger LOGGER = LogManager.getLogger(ID);
    private static final BCCommonSide common = BCCommonSide.with(ID);
    @OnlyHandledOnServer
    private static final Object server = Connection.makeServerSideUnknown(() -> DCServerSide.with(ID));
    @OnlyHandledOnClient
    private static final Object client = Connection.makeClientSideUnknown(() -> BCClientSide.with(ID));
    public static final ModDeferringManager DEFERRING_STATE = new ModDeferringManager(Braincell.ID);

    public Braincell() {
        super();
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(Braincell::commonSetupPhaseHook);
        common.modLoadingCallOut();
        Connection.doClientSide(() -> {
            modEventBus.addListener(Braincell::clientSetupPhaseHook);
            ((BCClientSide)client).modLoadingCallOut();
        });
        Connection.doServerSide(() -> {
            modEventBus.addListener(Braincell::serverSetupPhaseHook);
            ((DCServerSide)server).modLoadingCallOut();
        });
        DEFERRING_STATE.addRegistryDeferror(DeferrorType.RECIPE_SERIALIZER, BraincellRecipes.ENTRIES);
        DEFERRING_STATE.addRegistryDeferror(DeferrorType.ENTITY_TYPE, BraincellEntities.ENTRIES);
        DEFERRING_STATE.solveAndLockForeverEver();
    }

    public static void commonSetupPhaseHook(FMLCommonSetupEvent event) {
        common.postModLoadingPhaseCallOut();
    }

    public static void serverSetupPhaseHook(FMLDedicatedServerSetupEvent event) {
        ((DCServerSide)server).postModLoadingPhaseCallOut();
    }

    @OnlyIn(Dist.CLIENT)
    public static void clientSetupPhaseHook(FMLClientSetupEvent event) {
        ((BCClientSide)client).postModLoadingPhaseCallOut();
    }

    public static BCCommonSide common() {
        return common;
    }

    @OnlyIn(Dist.DEDICATED_SERVER)
    public static DCServerSide server() {
        return (DCServerSide)server;
    }

    @OnlyIn(Dist.CLIENT)
    public static BCClientSide client() {
        return (BCClientSide)client;
    }
}

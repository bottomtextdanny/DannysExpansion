package net.bottomtextdanny.dannys_expansion.common.hooks;

import net.bottomtextdanny.braincell.mod.entity.modules.data_manager.BCDataManagerProvider;
import net.bottomtextdanny.braincell.mod.packet_front.server_to_client.MSGUpdateDataManager;
import net.bottomtextdanny.danny_expannny.network.servertoclient.player.MSGSendPlayerAccessories;
import net.bottomtextdanny.danny_expannny.capabilities.CapabilityHelper;
import net.bottomtextdanny.danny_expannny.capabilities.player.PlayerAccessoryModule;
import net.bottomtextdanny.danny_expannny.capabilities.player.PlayerCapability;
import net.bottomtextdanny.danny_expannny.capabilities.player.PlayerHelper;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameRules;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.network.PacketDistributor;

public class ConnectionHooks {

    public static void trackEntityHook(PlayerEvent.StartTracking event){
        if (!event.getTarget().level.isClientSide()) {
            if (event.getTarget() instanceof BCDataManagerProvider dataHolder) {
                new MSGUpdateDataManager(event.getTarget().getId(), dataHolder.bcDataManager()).sendTo(PacketDistributor.PLAYER.with(() -> (ServerPlayer) event.getPlayer()));
            }

            if (event.getTarget() instanceof ServerPlayer player) {
                trackPlayerHook(player, event);
            }
        }
    }

    private static void trackPlayerHook(ServerPlayer player, PlayerEvent.StartTracking event) {
        PlayerAccessoryModule accessoryModule = CapabilityHelper.get(player, PlayerCapability.CAPABILITY).accessoryModule();
        new MSGSendPlayerAccessories(player.getId(), accessoryModule.getAccessories().getStackContents(), accessoryModule.getCoreAccessoryList()).sendTo(PacketDistributor.PLAYER.with(() -> (ServerPlayer) event.getPlayer()));
    }

    public static void playerLogInHook(PlayerEvent.PlayerLoggedInEvent event){
        if (!event.getPlayer().level.isClientSide() && event.getPlayer().isAlive()) {
            Player player = event.getPlayer();
            PlayerAccessoryModule accessoryModule = CapabilityHelper.get(player, PlayerCapability.CAPABILITY).accessoryModule();
            new MSGSendPlayerAccessories(player.getId(), accessoryModule.getAccessories().getStackContents(), accessoryModule.getCoreAccessoryList()).sendTo(PacketDistributor.PLAYER.with(() -> (ServerPlayer) event.getPlayer()));
        }
    }

    public static void copyPlayerHook(PlayerEvent.Clone event) {
        if (event.getEntity().level.getGameRules().getBoolean(GameRules.RULE_KEEPINVENTORY)) {
            PlayerAccessoryModule accessoryModuleO = PlayerHelper.accessoryModule(event.getPlayer());
            PlayerAccessoryModule accessoryModule = PlayerHelper.accessoryModule(event.getPlayer());

            for (int i = 0; i < PlayerAccessoryModule.CORE_ACCESSORIES_SIZE; i++) {
                accessoryModule.setAccessoryStack(i, accessoryModuleO.getAccessories().getItem(i));
            }
        }
    }
}

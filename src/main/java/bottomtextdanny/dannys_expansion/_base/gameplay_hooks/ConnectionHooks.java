package bottomtextdanny.dannys_expansion._base.gameplay_hooks;

import bottomtextdanny.dannys_expansion._base.network.servertoclient.player.MSGSendPlayerAccessories;
import bottomtextdanny.dannys_expansion._base.capabilities.player.DEAccessoryModule;
import bottomtextdanny.dannys_expansion._base.capabilities.player.PlayerCapability;
import bottomtextdanny.dannys_expansion._base.capabilities.player.PlayerHelper;
import bottomtextdanny.braincell.mod.capability.CapabilityHelper;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameRules;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.network.PacketDistributor;

public class ConnectionHooks {

    public static void trackEntityHook(PlayerEvent.StartTracking event){
        if (!event.getTarget().level.isClientSide()) {
            if (event.getTarget() instanceof ServerPlayer player) {
                trackPlayerHook(player, event);
            }
        }
    }

    private static void trackPlayerHook(ServerPlayer player, PlayerEvent.StartTracking event) {
        DEAccessoryModule accessoryModule = CapabilityHelper.get(player, PlayerCapability.TOKEN).accessoryModule();
        new MSGSendPlayerAccessories(player.getId(), accessoryModule.getAccessories().getStackContents(), accessoryModule.getCoreAccessoryList()).sendTo(PacketDistributor.PLAYER.with(() -> (ServerPlayer) event.getPlayer()));
    }

    public static void playerLogInHook(PlayerEvent.PlayerLoggedInEvent event){
        if (!event.getPlayer().level.isClientSide() && event.getPlayer().isAlive()) {
            Player player = event.getPlayer();
            DEAccessoryModule accessoryModule = CapabilityHelper.get(player, PlayerCapability.TOKEN).accessoryModule();
            new MSGSendPlayerAccessories(player.getId(), accessoryModule.getAccessories().getStackContents(), accessoryModule.getCoreAccessoryList()).sendTo(PacketDistributor.PLAYER.with(() -> (ServerPlayer) event.getPlayer()));
        }
    }

    public static void copyPlayerHook(PlayerEvent.Clone event) {
        if (event.getEntity().level.getGameRules().getBoolean(GameRules.RULE_KEEPINVENTORY)) {
            DEAccessoryModule accessoryModuleO = PlayerHelper.accessoryModule(event.getPlayer());
            DEAccessoryModule accessoryModule = PlayerHelper.accessoryModule(event.getPlayer());

            for (int i = 0; i < DEAccessoryModule.CORE_ACCESSORIES_SIZE; i++) {
                accessoryModule.setAccessoryStack(i, accessoryModuleO.getAccessories().getItem(i));
            }
        }
    }
}

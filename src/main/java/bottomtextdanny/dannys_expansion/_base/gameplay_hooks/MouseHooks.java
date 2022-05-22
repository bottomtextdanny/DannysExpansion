package bottomtextdanny.dannys_expansion._base.gameplay_hooks;

import bottomtextdanny.dannys_expansion._base.capabilities.player.PlayerCapability;
import bottomtextdanny.dannys_expansion._base.capabilities.player.PlayerGunModule;
import bottomtextdanny.dannys_expansion.content.items.gun.ScopingGun;
import bottomtextdanny.braincell.mod._mod.client_sided.events.MouseMovementEvent;
import bottomtextdanny.braincell.mod.capability.CapabilityHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public final class MouseHooks {

    public static void mouseSensibilityHook(MouseMovementEvent event) {
        final LocalPlayer player = Minecraft.getInstance().player;
        if (player != null && player.isAddedToWorld() && player.isAlive()) {
            final PlayerGunModule gunModule = CapabilityHelper.get(player, PlayerCapability.TOKEN).gunModule();

            if (gunModule.getGunScoping().getItem() instanceof ScopingGun scopingGun) {
                event.operateSensibilityMultiplication(MouseMovementEvent.MED_PRIORITY, scopingGun.scopingSensMult());
            }
        }
    }
}

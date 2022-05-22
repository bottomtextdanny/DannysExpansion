package bottomtextdanny.dannys_expansion._base.gameplay_hooks;

import bottomtextdanny.dannys_expansion._base.capabilities.player.PlayerHelper;
import bottomtextdanny.braincell.mod.capability.player.BCAccessoryModule;
import bottomtextdanny.braincell.mod.capability.player.accessory.extensions.FinnHurt;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import org.apache.commons.lang3.mutable.MutableObject;

public class HurtHooks {

    public static void livingHurtHook(LivingHurtEvent event) {
        if (event.getEntity() instanceof Player player) {
            handlePlayerHurt(player, event);
        }
    }

    private static void handlePlayerHurt(Player player, LivingHurtEvent event) {
        BCAccessoryModule accessoryModule = PlayerHelper.braincellAccessoryModule(player);
        MutableObject<Float> damageTransformer = new MutableObject<>(event.getAmount());
        accessoryModule.getAllAccessoryList().forEach(acc -> {
            if (acc instanceof FinnHurt hurtModule) {
                hurtModule.onHurt(event.getSource(), damageTransformer);
            }
        });
        event.setAmount(damageTransformer.getValue());
    }
}

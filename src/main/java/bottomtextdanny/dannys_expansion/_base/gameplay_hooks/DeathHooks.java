package bottomtextdanny.dannys_expansion._base.gameplay_hooks;

import bottomtextdanny.dannys_expansion._base.capabilities.player.DEAccessoryModule;
import bottomtextdanny.dannys_expansion._base.capabilities.player.PlayerHelper;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

public class DeathHooks {

    public static void livingDeathHook(LivingDeathEvent event) {
        if (event.getEntityLiving() instanceof Player player) {
            handlePlayerDeath(player);
        } else if (event.getEntityLiving() instanceof EnderDragon dragon) {
            handleEnderDragonDeath(dragon);
        }
    }

    private static void handlePlayerDeath(Player player) {
        DEAccessoryModule accessoryModule = PlayerHelper.accessoryModule(player);

        for (int i = 0; i < DEAccessoryModule.CORE_ACCESSORIES_SIZE; i++) {
            if (accessoryModule.getAccessories().getItem(i).getItem() != Items.AIR) {
                player.drop(accessoryModule.getAccessories().getItem(i), true, false);
            }
        }

        accessoryModule.getCoreAccessoryList().clear();
        accessoryModule.getAccessories().getStackContents().clear();
    }

    private static void handleEnderDragonDeath(EnderDragon dragon) {
//        LevelCapability capability = CapabilityHelper.get(dragon.level, LevelCapability.TOKEN);
//        capability.getPhaseModule().assertPhase(LevelPhaseModule.Phase.DRAGON);
//        LevelScheduleModule schedulesModule = capability.getScheduleModule();
//        schedulesModule.add(Sch.of(world -> {
//            BlockPos blockpos = world.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EndPodiumFeature.END_PODIUM_LOCATION).above(2);
//            EnderDragonRewardEntity reward = new EnderDragonRewardEntity(DEEntities.ENDER_DRAGON_REWARD.get(), world);
//            reward.absMoveTo(0.5, blockpos.getY(), 0.5, 0.0F, 0.0F);
//            world.addFreshEntity(reward);
//        }, 300));
    }
}

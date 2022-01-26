package net.bottomtextdanny.dannys_expansion.common.hooks;

import net.bottomtextdanny.danny_expannny.object_tables.DEEntities;
import net.bottomtextdanny.danny_expannny.objects.entities.EnderDragonRewardEntity;
import net.bottomtextdanny.dannys_expansion.core.Util.Sch;
import net.bottomtextdanny.danny_expannny.accessory.IAccessory;
import net.bottomtextdanny.danny_expannny.accessory.modules.FinnDeath;
import net.bottomtextdanny.danny_expannny.capabilities.CapabilityHelper;
import net.bottomtextdanny.danny_expannny.capabilities.player.PlayerAccessoryModule;
import net.bottomtextdanny.danny_expannny.capabilities.player.PlayerHelper;
import net.bottomtextdanny.danny_expannny.capabilities.world.LevelCapability;
import net.bottomtextdanny.danny_expannny.capabilities.world.LevelPhaseModule;
import net.bottomtextdanny.danny_expannny.capabilities.world.LevelScheduleModule;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.EndPodiumFeature;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

import java.util.List;

public class DeathHooks {

    public static void livingDeathHook(LivingDeathEvent event) {
        if (event.getEntityLiving() instanceof Player player) {
            handlePlayerDeath(player);
        } else if (event.getEntityLiving() instanceof EnderDragon dragon) {
            handleEnderDragonDeath(dragon);
        }
    }

    private static void handlePlayerDeath(Player player) {
        if (!player.level.getGameRules().getBoolean(GameRules.RULE_KEEPINVENTORY)) {
            PlayerAccessoryModule accessoryModule = PlayerHelper.accessoryModule(player);

            List<IAccessory> accessories = accessoryModule.getAllAccessoryList();

            for (int i = 0; i < PlayerAccessoryModule.ALL_ACCESSORIES_SIZE; i++) {
                IAccessory accessory = accessories.get(i);
                if (accessory instanceof FinnDeath deathModule) {
                    deathModule.onDeath();
                }
                if (i < PlayerAccessoryModule.CORE_ACCESSORIES_SIZE) {
                    player.drop(accessoryModule.getAccessories().getItem(i), true, false);
                }
            }

            accessoryModule.getCoreAccessoryList().clear();
            accessoryModule.getAccessories().getStackContents().clear();
        }
    }

    private static void handleEnderDragonDeath(EnderDragon dragon) {
        LevelCapability capability = CapabilityHelper.get(dragon.level, LevelCapability.CAPABILITY);
        capability.getPhaseModule().assertPhase(LevelPhaseModule.Phase.DRAGON);
        LevelScheduleModule schedulesModule = capability.getScheduleModule();
        schedulesModule.add(Sch.of(world -> {
            BlockPos blockpos = world.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EndPodiumFeature.END_PODIUM_LOCATION).above(2);
            EnderDragonRewardEntity reward = new EnderDragonRewardEntity(DEEntities.ENDER_DRAGON_REWARD.get(), world);
            reward.absMoveTo(0.5, blockpos.getY(), 0.5, 0.0F, 0.0F);
            world.addFreshEntity(reward);
        }, 300));
    }
}

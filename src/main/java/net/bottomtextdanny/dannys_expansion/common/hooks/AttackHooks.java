package net.bottomtextdanny.dannys_expansion.common.hooks;

import net.bottomtextdanny.braincell.mod.world.entity_utilities.EntityHurtCallout;
import net.bottomtextdanny.danny_expannny.accessory.modules.FinnHit;
import net.bottomtextdanny.danny_expannny.capabilities.player.PlayerAccessoryModule;
import net.bottomtextdanny.danny_expannny.capabilities.player.PlayerHelper;
import net.minecraft.world.damagesource.IndirectEntityDamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import org.apache.commons.lang3.mutable.MutableObject;

import java.util.Comparator;
import java.util.Objects;

public class AttackHooks {

    public static void damageLivingHook(LivingDamageEvent event) {
        if (event.getSource().getEntity() instanceof Player player) {
            playerDamageHook(player, event);
        }

        if (event.getEntityLiving() instanceof EntityHurtCallout) {
            event.setAmount(((EntityHurtCallout) event.getEntityLiving()).hurtCallout(event.getAmount(), event.getSource()));
        }
    }

    public static void playerDamageHook(Player player, LivingDamageEvent event) {
        if (player.isAlive()) {
            PlayerAccessoryModule accessoryModule = PlayerHelper.accessoryModule(player);
            MutableObject<Float> damageTransformer = new MutableObject<>(event.getAmount());
            if (event.getSource() instanceof IndirectEntityDamageSource source) {
                accessoryModule.getAllAccessoryList()
                        .stream()
                        .map(accessory -> accessory instanceof FinnHit module ? module : null)
                        .filter(Objects::nonNull)
                        .sorted(Comparator.comparingInt(FinnHit::hitModulePriority))
                        .forEach(accessory -> {
                            accessory.onIndirectAttack(event.getEntityLiving(), source, damageTransformer, event.getAmount());
                        });
                event.setAmount(damageTransformer.getValue());
            } else {
                accessoryModule.getAllAccessoryList()
                        .stream()
                        .map(accessory -> accessory instanceof FinnHit module ? module : null)
                        .filter(Objects::nonNull)
                        .sorted(Comparator.comparingInt(FinnHit::hitModulePriority))
                        .forEach(accessory -> {
                            accessory.onMeleeAttack(event.getEntityLiving(), event.getSource(), damageTransformer, event.getAmount());
                        });
                event.setAmount(damageTransformer.getValue());
            }
        }
    }

    public static void criticalHitHook(CriticalHitEvent event) {
        Player player = event.getPlayer();
        if (player.isAlive() && event.getTarget() instanceof LivingEntity livingTarget && event.isVanillaCritical()) {
            PlayerAccessoryModule accessoryModule = PlayerHelper.accessoryModule(player);
            MutableObject<Float> damageTransformer = new MutableObject<>(event.getDamageModifier());
            accessoryModule.getAllAccessoryList()
                    .stream()
                    .map(accessory -> accessory instanceof FinnHit module ? module : null)
                    .filter(Objects::nonNull)
                    .sorted(Comparator.comparingInt(FinnHit::critModulePriority))
                    .forEach(accessory -> {
                        accessory.onMeleeCritical(livingTarget, damageTransformer, event.getDamageModifier());
                    });

            event.setDamageModifier(damageTransformer.getValue());
        }
    }
}

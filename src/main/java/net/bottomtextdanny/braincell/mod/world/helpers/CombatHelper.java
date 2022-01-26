package net.bottomtextdanny.braincell.mod.world.helpers;

import com.google.common.collect.Sets;
import net.bottomtextdanny.danny_expannny.capabilities.CapabilityHelper;
import net.bottomtextdanny.danny_expannny.capabilities.world.LevelCapability;
import net.bottomtextdanny.danny_expannny.capabilities.world.LevelPhaseModule;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.SnowGolem;
import net.minecraft.world.entity.animal.horse.TraderLlama;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.WanderingTrader;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.Set;
import java.util.function.Predicate;

/**
 * this class contains methods for performing basic
 * numeric operations such as the elementary exponential, logarithm,
 * square root, and trigonometric functions.
 *
 */
public final class CombatHelper {

    public static void mayDisableShield(PathfinderMob attacker, Entity target, int ticks, float possibility) {
        if (target instanceof Player player && attacker.getRandom().nextFloat() < possibility) {
            if (player.isBlocking()) {
                if (!player.getCooldowns().isOnCooldown(player.getUseItem().getItem())) {
                    player.level.broadcastEntityEvent(target, (byte)30);
                }
                player.getCooldowns().addCooldown(player.getUseItem().getItem(), ticks);
                player.stopUsingItem();
            }
        }
    }

    public static void disableShield(PathfinderMob attacker, Entity target, int ticks) {
        mayDisableShield(attacker, target, ticks, 1.0F);
    }

    public static boolean validAttackTarget(PathfinderMob mob) {
        return mob.getTarget() != null && mob.getTarget().isAlive() && !mob.isRemoved();
    }

    public static void attackWithMultiplier(PathfinderMob attacker, LivingEntity livingEntity, float mult) {
        livingEntity.hurt(DamageSource.mobAttack(attacker), (float)attacker.getAttributeValue(Attributes.ATTACK_DAMAGE) * mult);
    }

    public static float getHealthNormalized(PathfinderMob attacker) {
        return attacker.getHealth() / attacker.getMaxHealth();
    }

    public static class EntityCheckHelper {
        public static final Set<Class<? extends LivingEntity>> GOODIES = Sets.newIdentityHashSet();
        public static final Set<Class<? extends LivingEntity>> POST_DRAGON_GOODIES = Sets.newIdentityHashSet();

        public static boolean isGoodie(LivingEntity entity) {
            return GOODIES.contains(entity.getClass());
        }

        public static boolean isPostDragonGoodie(LivingEntity entity) {
            return GOODIES.contains(entity.getClass()) || POST_DRAGON_GOODIES.contains(entity.getClass());
        }

        public static Predicate<LivingEntity> entityIsGoodPredicate(Level level) {
            if (CapabilityHelper.get(level, LevelCapability.CAPABILITY).getPhaseModule().getPhase() == LevelPhaseModule.Phase.DRAGON) {
                return EntityCheckHelper::isPostDragonGoodie;
            } else {
                return EntityCheckHelper::isGoodie;
            }
        }

        public static void init() {
            Set<Class<? extends LivingEntity>> set = GOODIES;

            set.add(IronGolem.class);
            set.add(SnowGolem.class);

            set = POST_DRAGON_GOODIES;

            set.add(TraderLlama.class);
            set.add(WanderingTrader.class);
            set.add(Villager.class);
        }
    }
}

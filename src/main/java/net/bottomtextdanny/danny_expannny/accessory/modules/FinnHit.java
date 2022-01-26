package net.bottomtextdanny.danny_expannny.accessory.modules;

import net.bottomtextdanny.danny_expannny.objects.entities.projectile.bullet.AbstractBulletEntity;
import net.bottomtextdanny.dannys_expansion.common.Entities.projectile.arrow.DannyArrowEntity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.IndirectEntityDamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Arrow;
import org.apache.commons.lang3.mutable.MutableObject;

public interface FinnHit {
    int H_ADD_OP = 0;
    int C_ADD_OP = 0, C_ECLIPSE = 1;

    int critModulePriority();

    int hitModulePriority();

    default void onMeleeAttack(LivingEntity entity, DamageSource source, MutableObject<Float> amount, float baseValue) {}

    default void onMeleeCritical(LivingEntity entity, MutableObject<Float> amount, float baseValue) {}

    default void onIndirectAttack(LivingEntity attacked, IndirectEntityDamageSource source, MutableObject<Float> amount, float baseValue) {
        if (source.getDirectEntity() instanceof Arrow || source.getDirectEntity() instanceof DannyArrowEntity) {
            onArrowAttack(attacked, source, amount, baseValue);
        } else if (source.getDirectEntity() instanceof AbstractBulletEntity) {
            onBulletAttack(attacked, source, amount, baseValue);
        }
    }

    default void onArrowAttack(LivingEntity attacked, IndirectEntityDamageSource source, MutableObject<Float> amount, float baseValue) {}

    default void onBulletAttack(LivingEntity attacked, IndirectEntityDamageSource source, MutableObject<Float> amount, float baseValue) {}
}

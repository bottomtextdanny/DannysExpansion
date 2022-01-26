package net.bottomtextdanny.dannys_expansion.core.data;

import net.bottomtextdanny.danny_expannny.objects.entities.projectile.bullet.AbstractBulletEntity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.IndirectEntityDamageSource;
import net.minecraft.world.entity.Entity;

import javax.annotation.Nullable;

public class DEDamageSources {
    public static final DamageSource CURSED_FLAMES = new DamageSource("cursed_flames").bypassArmor().bypassMagic();
    public static final DamageSource SPORES = new DamageSource("spores").bypassArmor().bypassMagic();
    public static final DamageSource VENOM = new DamageSource("venom").bypassArmor();

    public static DamageSource causeBulletDamage(AbstractBulletEntity source, @Nullable Entity indirectEntityIn) {
        return new IndirectEntityDamageSource("dannys_expansion.bullet", source, indirectEntityIn).setProjectile();
    }
}

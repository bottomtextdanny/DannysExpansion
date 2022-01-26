package net.bottomtextdanny.danny_expannny.objects.entities.mob.slime;

import net.bottomtextdanny.braincell.mod.base.misc.timer.IntScheduler;
import net.bottomtextdanny.danny_expannny.object_tables.DEParticles;
import net.bottomtextdanny.dannys_expansion.common.Entities.living.SlimeSummonEntity;
import net.bottomtextdanny.dannys_expansion.core.Util.Timer;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

public class SporeSlimeEntity extends SlimeSummonEntity {

    public SporeSlimeEntity(EntityType<? extends SporeSlimeEntity> type, Level worldIn) {
        super(type, worldIn);
        this.hopDelay = new Timer(40);
        this.max_life_ticks.set(200);
        this.useless_timer.set(IntScheduler.simple(50));
    }

    public static AttributeSupplier.Builder Attributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 30.0D)
                .add(Attributes.FOLLOW_RANGE, 20.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.0D)
                .add(Attributes.ATTACK_DAMAGE, 5.0D);
    }

    @Override
    public float getGelAmount() {
        return 0;
    }

    @Override
    public ParticleOptions getDeathParticle() {
        return DEParticles.DEATH_SPORER.get();
    }
}

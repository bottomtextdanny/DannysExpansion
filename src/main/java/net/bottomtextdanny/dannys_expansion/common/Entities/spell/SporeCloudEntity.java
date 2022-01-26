package net.bottomtextdanny.dannys_expansion.common.Entities.spell;

import net.bottomtextdanny.danny_expannny.object_tables.DEEffects;
import net.bottomtextdanny.danny_expannny.object_tables.DEParticles;
import net.bottomtextdanny.dannys_expansion.core.Util.particle.DannyParticleData;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.PushReaction;

import java.util.List;

public class SporeCloudEntity extends SpellEntity {

    public SporeCloudEntity(EntityType<? extends SpellEntity> entityTypeIn, Level worldIn) {
        super(entityTypeIn, worldIn);
        setLifeTime(500);
    }

    @Override
    public void tick() {
        super.tick();
        double d0 = this.random.nextGaussian() * (getBoundingBox().getXsize() + 1.5);
        double d1 = this.random.nextGaussian() * (getBoundingBox().getYsize() + 1.3);
        double d2 = this.random.nextGaussian() * (getBoundingBox().getXsize() + 1.5);
        List<LivingEntity> entities = this.level.getEntitiesOfClass(LivingEntity.class, getBoundingBox().inflate(3, 2.6, 3), o -> true);
        if (!entities.isEmpty()) {
            for (LivingEntity entity : entities) {
                int f = this.level.getDifficulty().getId() - 1;
                entity.addEffect(new MobEffectInstance(DEEffects.SPORES.get(), 210, f));
            }
        }

        if (this.random.nextInt(3) == 1 && getLifeTick() < 390) {
            if (!this.level.getBlockState(new BlockPos(getX() + d0, getY() + d1, getZ() + d2)).canOcclude()) {
                this.level.addParticle(new DannyParticleData(DEParticles.SPORE_GAS, 6), getX() + d0, getY() + d1, getZ() + d2, 0.0F, 0.0F, 0.0F);

            }
        }
    }

    public PushReaction getPistonPushReaction() {
        return PushReaction.IGNORE;
    }

    @Override
    public boolean movable() {
        return false;
    }
}

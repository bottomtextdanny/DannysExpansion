package net.bottomtextdanny.dannys_expansion.common.Entities.spell;

import net.bottomtextdanny.danny_expannny.object_tables.DEEffects;
import net.bottomtextdanny.danny_expannny.object_tables.DEParticles;
import net.bottomtextdanny.dannys_expansion.core.Util.qol.EntityUtil;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

public class CursedFireEntity extends SpellEntity {

    public CursedFireEntity(EntityType<? extends CursedFireEntity> entityTypeIn, Level worldIn) {
        super(entityTypeIn, worldIn);
        setLifeTime(120);
    }

    @Override
    public void tick() {
        super.tick();

        double p0 = getX() + this.random.nextGaussian() * 0.09375D;
        double p1 = getY() + 0.09375D + this.random.nextGaussian() * 0.09375D;
        double p2 = getZ() + this.random.nextGaussian() * 0.09375D;

        EntityUtil.particleAt(this.level, DEParticles.CURSED_FLAME.get(), 1, p0, p1, p2,0.0D, 0.0D, 0.0D, 0.0F);
        setDeltaMovement(getDeltaMovement().add(0.9 * this.forward.x, 0.9 * this.forward.y, 0.9 * this.forward.z));
    }

    @Override
    protected void onBlockHit(BlockHitResult p_230299_1_) {
        super.onBlockHit(p_230299_1_);
        setDeath();
    }

    @Override
    protected void onEntityHit(EntityHitResult p_213868_1_) {
        super.onEntityHit(p_213868_1_);
        if (p_213868_1_.getEntity() instanceof LivingEntity) {
            if (castersDamage((LivingEntity)p_213868_1_.getEntity(),2)) {
                ((LivingEntity) p_213868_1_.getEntity()).addEffect(new MobEffectInstance(DEEffects.CURSED_FLAMES.get(), 120, 0, false, true));
            }
        }
        setDeath();
    }
}

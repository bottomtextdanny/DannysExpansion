package net.bottomtextdanny.dannys_expansion.common.Entities.spell;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

public class IceBulletEntity extends SpellEntity {

    public IceBulletEntity(EntityType<? extends IceBulletEntity> entityTypeIn, Level worldIn) {
        super(entityTypeIn, worldIn);

        setLifeTime(120);
    }


    @Override
    public void tick() {
        super.tick();

//        if (new Random().nextInt(2) == 1) {
//            EntityUtil.particleAt(level, DannyParticles.TINY_SNOWFLAKE.get(), 1, position().x, position().y + 0.125F, position().z,0.0D, 0.0D, 0.0D, 0.0F);
//        }

        setDeltaMovement(getDeltaMovement().add(1.0 * this.forward.x, 1.0 * this.forward.y, 1.0 * this.forward.z));
    }

    @Override
    protected void onBlockHit(BlockHitResult p_230299_1_) {
        super.onBlockHit(p_230299_1_);
//        EntityUtil.particleAt(level, DannyParticles.SNOWFLAKE.get(), 1, this.getX(), this.getY() + 0.25F, this.getZ(),0.0D, 0.0D, 0.0D, 0.0F);
//        this.playSound(DannySounds.ES_ICE_BULLET_HIT.get(), 1.0F, (float) (1.0F + 0.2F * random.nextGaussian()));
//        setDeath();
    }

    @Override
    protected void onEntityHit(EntityHitResult p_213868_1_) {
        super.onEntityHit(p_213868_1_);
        if (p_213868_1_.getEntity() instanceof LivingEntity) {
            if (castersDamage((LivingEntity)p_213868_1_.getEntity(),3)) {
                ((LivingEntity) p_213868_1_.getEntity()).addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 200));

            }
        }
//        EntityUtil.particleAt(level, DannyParticles.SNOWFLAKE.get(), 1, this.getX(), this.getY() + 0.25F, this.getZ(),0.0D, 0.0D, 0.0D, 0.0F);
//        this.playSound(DannySounds.ES_ICE_BULLET_HIT.get(), 1.0F, (float) (1.0F + 0.2F * random.nextGaussian()));
        setDeath();
    }
}

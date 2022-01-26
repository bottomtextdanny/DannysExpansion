package net.bottomtextdanny.dannys_expansion.common.Entities.spell;

import net.bottomtextdanny.braincell.mod.entity.modules.animatable.builtin_animations.Animation;
import net.bottomtextdanny.danny_expannny.object_tables.DEEntities;
import net.bottomtextdanny.danny_expannny.object_tables.DEParticles;
import net.bottomtextdanny.danny_expannny.object_tables.DESounds;
import net.bottomtextdanny.dannys_expansion.common.Entities.living.MummyEntity;
import net.bottomtextdanny.dannys_expansion.common.Entities.living.SandScarabEntity;
import net.bottomtextdanny.dannys_expansion.core.Util.DEMath;
import net.bottomtextdanny.dannys_expansion.core.Util.DannyRayTraceHelper;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class SandScarabEggEntity extends SpellEntity {
	public final Animation breakAnimation = addAnimation(new Animation(5));
    public float prevMotionToRot;
    public float motionToRot;

    public SandScarabEggEntity(EntityType<? extends SpellEntity> entityTypeIn, Level worldIn) {
        super(entityTypeIn, worldIn);
        setLifeTime(120);
    }

    @Override
    public void tick() {
        super.tick();

        push(0, -0.08, 0);

        if (this.level.isClientSide) {
            float difference = DEMath.getHorizontalDistance(this, this.xOld, this.zOld);

            this.prevMotionToRot = this.motionToRot;
            this.motionToRot += difference;
        }

        if (this.breakAnimation.isWoke()) {
            if (this.mainHandler.getTick() == 3) {
                playSound(DESounds.ES_SAND_SCARAB_EGG_BREAK.get(), 1.0F, 1.0F);
            } else if (this.mainHandler.getTick() == 5) {
                if (this.level.isClientSide()) {
                    float yawOffset = 0;

                    for (int i = 0; i < 17; i++) {
                        float motionMultiplier = 0.2F + this.random.nextFloat() * 0.23F;
                        Vec3 vec1 = DEMath.fromPitchYaw(0, yawOffset + (float) this.random.nextGaussian() * 15).multiply(motionMultiplier, motionMultiplier, motionMultiplier);

                        this.level.addParticle(DEParticles.SAND_CLOUD.get(), getX(), getEyeY() + 0.25F, getZ(), vec1.x, 0.1F, vec1.z);

                        yawOffset += 20;
                    }
                }

                if (!this.level.isClientSide() && getCaster() instanceof MummyEntity) {
                    SandScarabEntity scarab = new SandScarabEntity(DEEntities.SAND_SCARAB.get(), this.level);

                    scarab.setTamed(true);
                    scarab.setSummoner((MummyEntity)getCaster());
                    scarab.setPos(getX(), getY(), getZ());
                    this.level.addFreshEntity(scarab);
                }
                setDeath();
            }

        }
    }

    @Override
    public void setDeath() {
        super.setDeath();
    }

    @Override
    public HitResult rayTraceResultType() {
        return DannyRayTraceHelper.orbRaytrace(this, this::collisionParameters, getDeltaMovement(), ClipContext.Fluid.NONE);
    }

    @Override
    protected void onBlockHit(BlockHitResult p_230299_1_) {
        super.onBlockHit(p_230299_1_);
        if (!this.level.isClientSide()) {
            if (!this.breakAnimation.isWoke()) {
                this.mainHandler.play(this.breakAnimation);
            }
        }
    }

    @Override
    protected void onEntityHit(EntityHitResult p_213868_1_) {
        super.onEntityHit(p_213868_1_);

        if (!this.level.isClientSide()) {
            if (!this.breakAnimation.isWoke()) {
                if (p_213868_1_.getEntity() instanceof LivingEntity) {
                    castersDamage((LivingEntity)p_213868_1_.getEntity(),2);
                }

                this.mainHandler.play(this.breakAnimation);
            }
        }
    }
}

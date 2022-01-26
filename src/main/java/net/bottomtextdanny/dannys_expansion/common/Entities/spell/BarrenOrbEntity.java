package net.bottomtextdanny.dannys_expansion.common.Entities.spell;

import net.bottomtextdanny.danny_expannny.object_tables.DEParticles;
import net.bottomtextdanny.danny_expannny.object_tables.DESounds;
import net.bottomtextdanny.dannys_expansion.common.Entities.living.BasicSummonEntity;
import net.bottomtextdanny.dannys_expansion.core.Util.DEMath;
import net.bottomtextdanny.dannys_expansion.core.Util.DannyRayTraceHelper;
import net.bottomtextdanny.braincell.underlying.misc.ObjectFetcher;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BarrenOrbEntity extends SpellEntity {
    @OnlyIn(Dist.CLIENT)
    public float spillYaw;

    public BarrenOrbEntity(EntityType<? extends BarrenOrbEntity> entityTypeIn, Level worldIn) {
        super(entityTypeIn, worldIn);
        setLifeTime(35);
    }

    @Override
    public void onLifeStart() {
        super.onLifeStart();
        playSound(DESounds.ES_BARREN_ORB_GENERATE.get(), 1.0F, 1.0F + this.random.nextFloat() * 0.1F);
    }

    @Override
    public void tick() {
        super.tick();
        if (getLifeTick() >= 14) {
            setDeltaMovement(1.6 * this.forward.x, 1.6 * this.forward.y, 1.6 * this.forward.z);

        } else {

            if (casterHasAttackTarget()) {
                float yaw = DEMath.getTargetYaw(this, getCasterTarget());
                float pitch = DEMath.getTargetPitch(this, getCasterTarget());
                setRotations(yaw, pitch);
            }
            if (this.level.isClientSide()) {

                if (getLifeTick() % 2 == 1) {
                    Vec3 vec0 = DEMath.fromPitchYaw(0, getYRot() + this.spillYaw).multiply(0.7, 0.7, 0.7);

                    this.level.addParticle(DEParticles.SAND_SMOKE.get(), position().x, position().y + 0.25F, position().z, vec0.x, 0, vec0.z);
                }
            }
        }
    }

    @Override
    public void onLifeEnd() {
        super.onLifeEnd();
        if (!this.level.isClientSide) {

            playSound(DESounds.ES_BARREN_ORB_IMPACT.get(), 1.0F, 1.0F + this.random.nextFloat() * 0.1F);
            sendClientMsg(0);
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void clientCallOutHandler(int flag, ObjectFetcher fetcher) {
        super.clientCallOutHandler(flag, fetcher);
        if (flag == 0) {
            for(int i = 0; i < 10; ++i) {
                Vec3 vec0 = DEMath.fromPitchYaw(10, this.random.nextFloat() * 360);

                this.level.addParticle(DEParticles.SAND_CLOUD.get(), this.getRandomX(0.6D), this.getRandomY() + 0.5, this.getRandomZ(0.6D), vec0.x, vec0.y, vec0.z);
                this.level.addParticle(DEParticles.SAND_SMOKE.get(), this.getRandomX(0.6D), this.getRandomY() + 0.5, this.getRandomZ(0.6D), vec0.x, vec0.y, vec0.z);
            }
        }
    }

    @Override
    public HitResult rayTraceResultType() {
        return DannyRayTraceHelper.orbRaytrace(this, this::collisionParameters, getDeltaMovement().add(getSimpleMotion()), ClipContext.Fluid.ANY);

    }

    @Override
    protected void onBlockHit(BlockHitResult p_230299_1_) {
        super.onBlockHit(p_230299_1_);
        setDeath();
    }

    @Override
    protected void onEntityHit(EntityHitResult p_213868_1_) {
        super.onEntityHit(p_213868_1_);
        if (!this.level.isClientSide()) {

            if (p_213868_1_.getEntity() instanceof LivingEntity) {

                if (p_213868_1_.getEntity() instanceof BasicSummonEntity) {

                    if (((BasicSummonEntity) p_213868_1_.getEntity()).getSummoner() != getCaster()) {
                        castersDamage((LivingEntity)p_213868_1_.getEntity(),5);
                        setDeath();
                    }
                } else {
                    castersDamage((LivingEntity)p_213868_1_.getEntity(),5);
                    setDeath();
                }

            }


        }

    }
}

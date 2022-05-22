package bottomtextdanny.dannys_expansion.content.entities.projectile;

import bottomtextdanny.dannys_expansion.content.entities.mob.klifour.Klifour;
import bottomtextdanny.dannys_expansion.tables._client.DEParticles;
import bottomtextdanny.dannys_expansion.tables.DESounds;
import bottomtextdanny.dannys_expansion._util.DERayUtil;
import bottomtextdanny.dannys_expansion._util.EntityUtil;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class KlifourSpitEntity extends SpellEntity {

    public KlifourSpitEntity(EntityType<? extends SpellEntity> entityTypeIn, Level worldIn) {
        super(entityTypeIn, worldIn);
        setLifeTime(30);
    }

    @Override
    public void tick() {
        super.tick();
        this.noPhysics = true;

        if (this.level.isClientSide) {
            double xRand0 = this.random.nextGaussian() * 0.2;
            double yRand0 = this.random.nextGaussian() * 0.2;
            double zRand0 = this.random.nextGaussian() * 0.2;
            double xRand1 = this.random.nextGaussian() * 0.2;
            double yRand1 = this.random.nextGaussian() * 0.2;
            double zRand1 = this.random.nextGaussian() * 0.2;
            if (this.random.nextInt(2) == 1)
                this.level.addParticle(DEParticles.KLIFOUR_POISON_BUBBLE.get(), position().x + xRand0, position().y + 0.125F + yRand0, position().z + zRand0,0.0D, 0.0D, 0.0D);
            else this.level.addParticle(DEParticles.KLIFOUR_POISON_SMALL_BUBBLE.get(), position().x + xRand0, position().y + 0.125F + yRand0, position().z + zRand0,0.0D, 0.0D, 0.0D);
            if (this.random.nextInt(2) == 1)
                this.level.addParticle(DEParticles.KLIFOUR_POISON_DRIP.get(), position().x + xRand1, position().y + 0.125F + yRand1, position().z + zRand1,0.0D, 0.0D, 0.0D);
            else this.level.addParticle(DEParticles.KLIFOUR_POISON_BIG_DRIP.get(), position().x + xRand1, position().y + 0.125F + yRand1, position().z + zRand1,0.0D, 0.0D, 0.0D);
        }

        setDeltaMovement(new Vec3(1.2 * this.forward.x, 1.2 * this.forward.y, 1.2 * this.forward.z));
        super.checkInsideBlocks();
    }

    @Override
    public void setDeath() {
        super.setDeath();
        playSound(DESounds.ES_KLIFOUR_SPIT_IMPACT.get(), 1.0F, 1.0F);

        EntityUtil.particleAt(this.level, DEParticles.KLIFOUR_POISON_BIG_DRIP.get(), 10, getX(), getY(), getZ(), this.random.nextGaussian() * 0.2, this.random.nextGaussian() * 0.2, this.random.nextGaussian() * 0.2, 0);
    }

    @Override
    protected void onBlockHit(BlockHitResult result) {
        super.onBlockHit(result);
        setDeath();
    }

    @Override
    protected void onEntityHit(EntityHitResult result) {
        super.onEntityHit(result);
        if (!(result.getEntity() instanceof Klifour)) {
            if (result.getEntity() instanceof LivingEntity) {
                castersDamage((LivingEntity)result.getEntity(),2);

                if (this.random.nextInt(8) == 2 * this.level.getDifficulty().getId()) {
                    ((LivingEntity) result.getEntity()).addEffect(new MobEffectInstance(MobEffects.POISON, 80));
                }
            }
            setDeath();
        }
    }

    @Override
    public HitResult rayTraceResultType() {
        return DERayUtil.orbRaytrace(this, this::collisionParameters, getDeltaMovement().add(this.externalMotion.getAcceleratedMotion()), ClipContext.Fluid.ANY, 0.3F);
    }

    @Override
    protected void checkInsideBlocks() {
       super.checkInsideBlocks();
    }
}
